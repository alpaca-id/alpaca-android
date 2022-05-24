package com.bangkit.alpaca.ui.customization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import com.bangkit.alpaca.databinding.ModalBottomSheetTextAlignmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class BottomSheetAlignmentSetting : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetTextAlignmentBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetTextAlignmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            btnSave.setOnClickListener {
                val chips = this.chipGroup
                val selectedChip = chips.children
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }

                Toast.makeText(requireContext(), selectedChip.first(), Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "BottomSheetAlignmentSet"
    }
}