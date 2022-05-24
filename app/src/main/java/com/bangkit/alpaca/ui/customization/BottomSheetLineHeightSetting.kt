package com.bangkit.alpaca.ui.customization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bangkit.alpaca.databinding.ModalBottomSheetLineHeightBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetLineHeightSetting : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetLineHeightBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetLineHeightBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            btnSave.setOnClickListener {
                val chip = this.chipGroup.checkedChipId
                Toast.makeText(requireContext(), chip.toString(), Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "BottomSheetLineHeightSetting"
    }
}