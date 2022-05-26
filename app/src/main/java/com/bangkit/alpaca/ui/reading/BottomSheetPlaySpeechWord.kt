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
    private var isReady = false

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

        binding?.chipGroupMode?.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds[0] == R.id.chip_word) {
                binding?.tvSelectedWord?.text = word
            } else {
                val wordWithSeparate = word?.replace("\\B".toRegex(), "-")
                binding?.tvSelectedWord?.text = wordWithSeparate
            }
        }
    }

    private fun setupPlayAction() {
        binding?.btnPlayWord?.setOnClickListener {
            if (!isReady) {
                "preparing...".showToastMessage(requireContext())
                mediaPlayer?.prepareAsync()
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
    }

    private fun initMediaPlayer() {

        val lang = "id"
        val word = arguments?.getString(EXTRA_WORD)?.trim()

        val audioUrl =
            "https://translate.google.com/translate_tts?ie=UTF-8&tl=${lang}&client=tw-ob&q=${word}"

        mediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        mediaPlayer?.setAudioAttributes(attribute)

        try {
            mediaPlayer?.setDataSource(audioUrl)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaPlayer?.setOnPreparedListener {
            isReady = true
            mediaPlayer?.start()
        }

        mediaPlayer?.setOnErrorListener { _, _, _ -> false }
    }

    companion object {
        const val EXTRA_WORD = "extra_word"
    }
}