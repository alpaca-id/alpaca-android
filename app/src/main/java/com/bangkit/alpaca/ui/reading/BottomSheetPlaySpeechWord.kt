package com.bangkit.alpaca.ui.reading

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bangkit.alpaca.R
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.databinding.ModalBottomSheetPlayWordBinding
import com.bangkit.alpaca.utils.showToastMessage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class BottomSheetPlaySpeechWord : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetPlayWordBinding? = null
    private val binding get() = _binding
    private val readingViewModel: ReadingViewModel by viewModels()
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
        resultAudio()
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

        binding

        binding?.btnPlayWord?.setOnClickListener {
            readingViewModel.getTextToSpeech(mWord)
            binding?.btnPlayWord?.playAnimation()
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
                    start()
                }
                setOnCompletionListener {
                    stop()
                    binding?.btnPlayWord?.progress = 0f
                    binding?.btnPlayWord?.pauseAnimation()
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
        const val EXTRA_WORD = "extra_word"
    }
}