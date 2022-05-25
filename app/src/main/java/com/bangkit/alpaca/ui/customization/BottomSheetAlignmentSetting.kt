package com.bangkit.alpaca.ui.customization

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.bangkit.alpaca.databinding.ModalBottomSheetTextAlignmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetAlignmentSetting : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetTextAlignmentBinding? = null
    private val binding get() = _binding

    private val customizationViewModel: CustomizationViewModel by activityViewModels()

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

        customizationViewModel.textAlignmentPreference.observe(viewLifecycleOwner) { alignType ->
            when (alignType) {
                Gravity.START -> binding?.textAlignLeft?.isChecked = true
                Gravity.CENTER -> binding?.textAlignCenter?.isChecked = true
                Gravity.END -> binding?.textAlignRight?.isChecked = true
            }
        }

        binding?.apply {
            btnSave.setOnClickListener {
                val chips = this.chipGroup
                val selectedChip = chips.children
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }
                    .first()

                val type = when (selectedChip) {
                    "Kiri" -> Gravity.START
                    "Tengah" -> Gravity.CENTER
                    else -> Gravity.END
                }

                customizationViewModel.saveTextAlignmentPreference(type)
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