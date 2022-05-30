package com.bangkit.alpaca.ui.customization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.bangkit.alpaca.databinding.ModalBottomSheetLineSpacingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetLineSpacingSetting : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetLineSpacingBinding? = null
    private val binding get() = _binding

    private val customizationViewModel: CustomizationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetLineSpacingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customizationViewModel.lineSpacingPreference.observe(viewLifecycleOwner) { lineSpacing ->
            setChipsState(lineSpacing)
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
                .map { (it as Chip).text.toString().toIntOrNull() }
                .first()

            customizationViewModel.saveLineSpacingPreferences(selectedChip ?: 0)
        }
    }

    /**
     * Highlight the selected chip based on the lineSpacing
     *
     * @param lineSpacing Int
     */
    private fun setChipsState(lineSpacing: Int) {
        binding?.apply {
            when (lineSpacing) {
                0 -> chipSpacing1.isChecked = true
                1 -> chipSpacing2.isChecked = true
                2 -> chipSpacing3.isChecked = true
                3 -> chipSpacing4.isChecked = true
            }
        }
    }

    companion object {
        const val TAG = "BottomSheetLineSpacing"
    }
}