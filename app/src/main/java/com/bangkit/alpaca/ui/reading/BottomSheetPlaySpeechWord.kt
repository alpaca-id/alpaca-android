package com.bangkit.alpaca.ui.reading

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ModalBottomSheetPlayWordBinding
import com.bangkit.alpaca.utils.showToastMessage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.IOException

class BottomSheetPlaySpeechWord : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetPlayWordBinding? = null
    private val binding get() = _binding
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var mWord: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetPlayWordBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWord()
        setupPlayAction()
        initMediaPlayer()
    }

    private fun setupWord() {
        val word = arguments?.getString(EXTRA_WORD)?.trim()
        binding?.tvSelectedWord?.text = word
        mWord = word ?: ""

        binding?.chipGroupMode?.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds[0] == R.id.chip_word) {
                mediaPlayer?.stop()
                binding?.tvSelectedWord?.text = word
                mWord = word ?: ""

            } else {
                mediaPlayer?.stop()
                val wordWithSeparate = word?.replace("\\B".toRegex(), "-")
                binding?.tvSelectedWord?.text = wordWithSeparate

                val wordWithSpace = word?.replace("\\B".toRegex(), " ")
                mWord = wordWithSpace ?: ""
            }
        }
    }

    private fun setupPlayAction() {
        binding?.btnPlayWord?.setOnClickListener {
            "preparing...".showToastMessage(requireContext())
            mediaPlayerPrepare()
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

    private fun mediaPlayerPrepare() {
        val lang = "id"
        val audioUrl =
            "https://translate.google.com/translate_tts?ie=UTF-8&tl=${lang}&client=tw-ob&q=${this.mWord}"

        try {
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(audioUrl)
            mediaPlayer?.prepareAsync()
            mediaPlayer?.setOnPreparedListener {
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
        const val EXTRA_WORD = "extra_word"
    }
}