package com.bangkit.alpaca.ui.customization

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ActivityCustomizationBinding

class CustomizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomizationBinding

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
}