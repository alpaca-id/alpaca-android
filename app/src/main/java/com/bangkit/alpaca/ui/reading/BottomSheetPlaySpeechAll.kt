package com.bangkit.alpaca.ui.reading

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.alpaca.databinding.ModalBottomSheetPlayAllBinding
import com.bangkit.alpaca.model.Sentence
import com.bangkit.alpaca.ui.adapter.SentencesListAdapter
import com.bangkit.alpaca.utils.showToastMessage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.IOException

class BottomSheetPlaySpeechAll : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetPlayAllBinding? = null
    private val binding get() = _binding
    private val sentencesListAdapter by lazy { SentencesListAdapter() }
    private var mediaPlayer: MediaPlayer? = null
    private var isReady = false
    private var mSentence = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetPlayAllBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        setupListSentence()
        initMediaPlayer()
    }

    private fun setupAction() {
        sentencesListAdapter.setOnItemClickCallback(object :
            SentencesListAdapter.OnItemClickCallback {
            override fun onItemClicked(sentence: String, btn: ImageButton) {
                if (!isReady || mSentence != sentence) {
                    "preparing...".showToastMessage(requireContext())
                    mediaPlayerPrepare(sentence)
                } else {
                    if (mediaPlayer?.isPlaying as Boolean) {
                        "pause...".showToastMessage(requireContext())
                        mediaPlayer?.pause()
                    } else {
                        "playing...".showToastMessage(requireContext())
                        mediaPlayer?.start()
                    }
                }
            }
        })
    }

    private fun setupListSentence() {
        val sentences = mutableListOf<Sentence>()
        val bundle = arguments?.getStringArrayList(EXTRA_SENTENCES)
        bundle?.forEach { string ->
            sentences.add(Sentence(string, false))
        }

        sentencesListAdapter.submitList(sentences)

        binding?.rvSentence?.apply {
            adapter = sentencesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mediaPlayer?.setAudioAttributes(attribute)
        mediaPlayer?.setOnErrorListener { _, _, _ -> false }
    }

    private fun mediaPlayerPrepare(sentence: String) {
        val lang = "id"
        val audioUrl =
            "https://translate.google.com/translate_tts?ie=UTF-8&tl=${lang}&client=tw-ob&q=${sentence}"

        try {
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(audioUrl)
            mediaPlayer?.prepareAsync()
            mediaPlayer?.setOnPreparedListener {
                isReady = true
                mSentence = sentence
                mediaPlayer?.start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mediaPlayer?.stop()
    }

    companion object {
        const val EXTRA_SENTENCES = "extra sentences"
    }
}