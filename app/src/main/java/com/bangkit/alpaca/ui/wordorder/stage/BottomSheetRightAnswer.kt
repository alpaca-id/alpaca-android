package com.bangkit.alpaca.ui.wordorder.stage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.alpaca.databinding.ModalBottomSheetRightAnswerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetRightAnswer : BottomSheetDialogFragment() {
    private var _binding: ModalBottomSheetRightAnswerBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetRightAnswerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnContinue?.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}