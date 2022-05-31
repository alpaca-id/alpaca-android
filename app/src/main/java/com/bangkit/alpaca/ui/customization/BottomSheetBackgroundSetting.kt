package com.bangkit.alpaca.ui.customization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ModalBottomSheetBackgroundColorBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class BottomSheetBackgroundSetting : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetBackgroundColorBinding? = null
    private val binding get() = _binding

    private val customizationViewModel: CustomizationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetBackgroundColorBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customizationViewModel.textBackgroundPreference.observe(viewLifecycleOwner) { backgroundColor ->
            setChipsState(backgroundColor)
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
                getString(R.string.white) -> 0
                getString(R.string.yellow) -> 1
                getString(R.string.blue) -> 2
                getString(R.string.grey) -> 3
                else -> 4
            }

            customizationViewModel.saveTextBackgroundPreferences(type)
        }
    }

    /**
     * Highlight the selected chip based on the alignType
     *
     * @param backgroundColor Int
     */
    private fun setChipsState(backgroundColor: Int) {
        binding?.apply {
            when (backgroundColor) {
                0 -> bgWhite.isChecked = true
                1 -> bgWarm.isChecked = true
                2 -> bgBlue.isChecked = true
                3 -> bgGrey.isChecked = true
                4 -> bgBlack.isChecked = true
            }
        }
    }

    companion object {
        const val TAG = "BottomSheetBackground"
    }
}