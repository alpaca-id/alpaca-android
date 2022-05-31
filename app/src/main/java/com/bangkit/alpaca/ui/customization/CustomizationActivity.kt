package com.bangkit.alpaca.ui.customization

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

        startObservingData()
        setViewsAction()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /**
     * Observing data from the ViewModel and set state to the views
     */
    private fun startObservingData() {
        customizationViewModel.apply {
            textSizePreference.observe(this@CustomizationActivity) { textSize ->
                binding.textView.textSize = textSize.toFloat()
                binding.tvTextSizeStatus.text = getString(R.string.pixel_suffix, textSize)
            }

            lineSpacingPreference.observe(this@CustomizationActivity) { lineSpacing ->
                binding.textView.letterSpacing = (lineSpacing * 0.05).toFloat()
                binding.tvLineSpacingStatus.text = getString(R.string.prefix_plus, lineSpacing)
            }

            textAlignmentPreference.observe(this@CustomizationActivity) { alignmentType ->
                binding.textView.gravity = alignmentType

                val alignmentTypeString = when (alignmentType) {
                    Gravity.START -> getString(R.string.left)
                    Gravity.CENTER -> getString(R.string.center)
                    else -> getString(R.string.right)
                }

                binding.tvAlignmentStatus.text = alignmentTypeString
            }

            lineHeightPreference.observe(this@CustomizationActivity) { lineHeight ->
                binding.textView.setLineSpacing(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        (lineHeight * 8).toFloat(),
                        resources.displayMetrics
                    ), 1f
                )
                binding.tvLineHeightStatus.text = lineHeight.toString()
            }

            textBackgroundPreference.observe(this@CustomizationActivity) { backgroundColor ->
                val selectedColor = when (backgroundColor) {
                    0 -> getColor(R.color.bg_white)
                    1 -> getColor(R.color.bg_warm)
                    2 -> getColor(R.color.bg_blue)
                    3 -> getColor(R.color.bg_dark)
                    else -> getColor(R.color.bg_black)
                }

                if (backgroundColor > 2) {
                    binding.textView.setTextColor(getColor(R.color.white))
                    binding.toolbar.setTitleTextColor(getColor(R.color.white))
                    binding.toolbar.setNavigationIconTint(getColor(R.color.white))
                } else {
                    binding.textView.setTextColor(getColor(R.color.black))
                    binding.toolbar.setTitleTextColor(getColor(R.color.black))
                    binding.toolbar.setNavigationIconTint(getColor(R.color.black))
                }

                binding.root.setBackgroundColor(selectedColor)
                binding.tvBackgroundStatus.setImageDrawable(ColorDrawable(selectedColor))
                binding.viewCardsBg.setBackgroundColor(selectedColor)

            }
        }
    }

    private fun setViewsAction() {
        binding.apply {
            cardTextSize.setOnClickListener {
                BottomSheetTextSizeSetting().show(
                    supportFragmentManager,
                    BottomSheetTextSizeSetting.TAG
                )
            }

            cardAlignment.setOnClickListener {
                BottomSheetAlignmentSetting().show(
                    supportFragmentManager,
                    BottomSheetAlignmentSetting.TAG
                )
            }

            cardLineHeight.setOnClickListener {
                BottomSheetLineHeightSetting().show(
                    supportFragmentManager,
                    BottomSheetLineHeightSetting.TAG
                )
            }

            cardLineSpacing.setOnClickListener {
                BottomSheetLineSpacingSetting().show(
                    supportFragmentManager,
                    BottomSheetLineSpacingSetting.TAG
                )
            }

            cardFontStyle.setOnClickListener {
                BottomSheetFontFamilySetting().show(
                    supportFragmentManager,
                    BottomSheetFontFamilySetting.TAG
                )
            }

            cardBackgroundColor.setOnClickListener {
                BottomSheetBackgroundSetting().show(
                    supportFragmentManager,
                    BottomSheetBackgroundSetting.TAG
                )
            }
        }
    }
}