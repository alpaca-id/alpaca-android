package com.bangkit.alpaca.ui.customization

import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.util.TypedValue
import android.view.Gravity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.model.DocumentData
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ActivityCustomizationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomizationBinding
    private val customizationViewModel: CustomizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.customization)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        customizationViewModel.textSizePreference.observe(this) { textSize ->
            binding.textView.textSize = textSize.toFloat()
            binding.tvTextSizeStatus.text = getString(R.string.pixel_suffix, textSize)
        }

        customizationViewModel.textAlignmentPreference.observe(this) { alignmentType ->
            binding.textView.gravity = alignmentType

            val alignmentTypeString = when (alignmentType) {
                Gravity.START -> "Kiri"
                Gravity.CENTER_HORIZONTAL -> "Tengah"
                Gravity.END -> "Kanan"
                else -> "Justify"
            }

            binding.tvAlignmentStatus.text = alignmentTypeString
        }

        customizationViewModel.lineHeightPreference.observe(this) { lineHeight ->
            binding.textView.setLineSpacing(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    (lineHeight * 8).toFloat(),
                    resources.displayMetrics
                ), 1f
            )
            binding.tvLineHeightStatus.text = lineHeight.toString()
        }

        customizationViewModel.lineSpacingPreference.observe(this) { lineSpacing ->
            binding.textView.letterSpacing = (lineSpacing * 0.05).toFloat()
            binding.tvLineSpacingStatus.text = getString(R.string.prefix_plus, lineSpacing)
        }

        binding.apply {
            cardTextSize.setOnClickListener {
                val modalTextSizeSetting = BottomSheetTextSizeSetting()
                modalTextSizeSetting.show(supportFragmentManager, BottomSheetTextSizeSetting.TAG)
            }

            cardAlignment.setOnClickListener {
                val modalAlignmentSetting = BottomSheetAlignmentSetting()
                modalAlignmentSetting.show(supportFragmentManager, BottomSheetAlignmentSetting.TAG)
            }

            cardLineHeight.setOnClickListener {
                val modalLineHeightSetting = BottomSheetLineHeightSetting()
                modalLineHeightSetting.show(
                    supportFragmentManager,
                    BottomSheetLineHeightSetting.TAG
                )
            }

            cardLineSpacing.setOnClickListener {
                val modalLineSpacingSetting = BottomSheetLineSpacingSetting()
                modalLineSpacingSetting.show(
                    supportFragmentManager,
                    BottomSheetLineSpacingSetting.TAG
                )
            }

            cardFontStyle.setOnClickListener {
                val modalFontFamilySetting = BottomSheetFontFamilySetting()
                modalFontFamilySetting.show(
                    supportFragmentManager,
                    BottomSheetFontFamilySetting.TAG
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val TAG = "CustomizationActivity"
    }
}