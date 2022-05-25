package com.bangkit.alpaca.ui.customization

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.bangkit.alpaca.R
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
            setChipsState(alignType)
        }

        binding?.apply {
            btnSave.setOnClickListener {
                saveSelectedChipState()
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Save selected chip data to the database
     */
    private fun saveSelectedChipState() {
        binding?.apply {
            val selectedChip = this.chipGroup.children
                .filter { (it as Chip).isChecked }
                .map { (it as Chip).text.toString() }
                .first()

            val type = when (selectedChip) {
                getString(R.string.left) -> Gravity.START
                getString(R.string.center) -> Gravity.CENTER
                else -> Gravity.END
            }

            customizationViewModel.saveTextAlignmentPreference(type)
        }
    }

    /**
     * Highlight the selected chip based on the alignType
     *
     * @param alignType Int
     */
    private fun setChipsState(alignType: Int) {
        binding?.apply {
            when (alignType) {
                Gravity.START -> textAlignLeft.isChecked = true
                Gravity.CENTER -> textAlignCenter.isChecked = true
                Gravity.END -> textAlignRight.isChecked = true
            }
        }
    }

    companion object {
        const val TAG = "BottomSheetAlignmentSet"
    }
}