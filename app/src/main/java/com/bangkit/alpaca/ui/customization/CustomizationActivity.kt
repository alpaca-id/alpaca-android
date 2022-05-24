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

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}