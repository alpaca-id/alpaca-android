package com.bangkit.alpaca.ui.reading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ModalBottomSheetPlayWordBinding
import com.bangkit.alpaca.utils.showToastMessage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetPlaySpeechWord : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetPlayWordBinding? = null
    private val binding get() = _binding

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
    }

    private fun setupWord() {
        val word = arguments?.getString(EXTRA_WORD)?.trim()
        binding?.tvSelectedWord?.text = word

        binding?.chipGroupMode?.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds[0] ==R.id.chip_word){
                binding?.tvSelectedWord?.text = word
            }else{
                val wordWithSeparate = word?.replace("\\B".toRegex(), "-")
                binding?.tvSelectedWord?.text = wordWithSeparate
            }
        }
    }

    private fun setupPlayAction() {
        binding?.btnPlayWord?.setOnClickListener {
            "Play...".showToastMessage(requireContext())
        }
    }

    companion object {
        const val EXTRA_WORD = "extra_word"
    }
}