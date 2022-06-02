package com.bangkit.alpaca.ui.wordorder.stage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.alpaca.databinding.ModalBottomSheetWrongAnswerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetWrongAnswer : BottomSheetDialogFragment() {
    private var _binding: ModalBottomSheetWrongAnswerBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetWrongAnswerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnTryAgain?.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}