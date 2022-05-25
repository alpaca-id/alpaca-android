package com.bangkit.alpaca.ui.customization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.bangkit.alpaca.databinding.ModalBottomSheetLineHeightBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetLineHeightSetting : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetLineHeightBinding? = null
    private val binding get() = _binding

    private val customizationViewModel: CustomizationViewModel by activityViewModels()

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

        customizationViewModel.lineHeightPreference.observe(viewLifecycleOwner) { lineHeight ->
            when (lineHeight) {
                1 -> binding?.chipLine1?.isChecked = true
                2 -> binding?.chipLine2?.isChecked = true
                3 -> binding?.chipLine3?.isChecked = true
                4 -> binding?.chipLine4?.isChecked = true
            }
        }

        binding?.apply {
            btnSave.setOnClickListener {
                val chips = this.chipGroup
                val selectedChip = chips.children
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString().toIntOrNull() }
                    .first()

                customizationViewModel.saveLineHeightPreference(selectedChip ?: 1)
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