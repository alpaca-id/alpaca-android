package com.bangkit.alpaca.ui.customization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.bangkit.alpaca.databinding.ModalBottomSheetTextSizeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetTextSizeSetting : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetTextSizeBinding? = null
    private val binding get() = _binding

    private val customizationViewModel: CustomizationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetTextSizeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customizationViewModel.textSizePreference.observe(viewLifecycleOwner) { textSize ->
            setChipsState(textSize)
        }

        binding?.btnSave?.setOnClickListener {
            saveSelectedChipState()
            dismiss()
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
            val chips = this.chipGroup.children
                .filter { (it as Chip).isChecked }
                .map {
                    (it as Chip).text.toString()
                        .replace("[^0-9]".toRegex(), "")
                        .toIntOrNull()
                }
                .first()

            customizationViewModel.saveTextSizePreference(chips ?: 16)
        }
    }

    /**
     * Highlight the selected chip based on the textSize
     *
     * @param textSize Int
     */
    private fun setChipsState(textSize: Int) {
        binding?.apply {
            when (textSize) {
                16 -> textSize16.isChecked = true
                18 -> textSize18.isChecked = true
                20 -> textSize20.isChecked = true
                24 -> textSize24.isChecked = true
                28 -> textSize28.isChecked = true
                32 -> textSize32.isChecked = true
                38 -> textSize38.isChecked = true
            }
        }
    }

    companion object {
        const val TAG = "TextSizeSetting"
    }
}