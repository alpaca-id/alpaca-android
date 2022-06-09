package com.bangkit.alpaca.ui.reading

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.databinding.ModalBottomSheetPlayAllBinding
import com.bangkit.alpaca.model.Sentence
import com.bangkit.alpaca.ui.adapter.SentencesListAdapter
import com.bangkit.alpaca.utils.showToastMessage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class BottomSheetPlaySpeechAll : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetPlayAllBinding? = null
    private val binding get() = _binding
    private val readingViewModel: ReadingViewModel by viewModels()
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
        resultAudio()
    }

    private fun setupAction() {
        sentencesListAdapter.setOnItemClickCallback(object :
            SentencesListAdapter.OnItemClickCallback {
            override fun onItemClicked(sentence: Sentence, position:Int) {
                if (!isReady || mSentence != sentence.text) {
                    if (mediaPlayer?.isPlaying as Boolean) {
                        mediaPlayer?.stop()
                    }
                    mSentence = sentence.text
                    readingViewModel.getTextToSpeech(sentence.text)
                } else {
                    if (mediaPlayer?.isPlaying as Boolean) {
                        mediaPlayer?.pause()
                    } else {
                        mediaPlayer?.start()
                    }
                }

                mediaPlayer?.setOnCompletionListener {
                    sentence.isPlaying = false
                    sentencesListAdapter.notifyItemChanged(position)
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

    private fun resultAudio() {
        readingViewModel.audio.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> mediaPlayerPrepare(result.data.audioUrl)
                is Result.Error -> result.error.showToastMessage(requireContext())
            }
        }
    }

    private fun mediaPlayerPrepare(audioUrl: String) {
        try {
            mediaPlayer?.apply {
                reset()
                setDataSource(audioUrl)
                prepareAsync()
                setOnPreparedListener {
                    isReady = true
                    start()
                }
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