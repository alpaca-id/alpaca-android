package com.bangkit.alpaca.ui.processing

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.alpaca.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProcessingActivity : AppCompatActivity() {

    private val processingViewModel: ProcessingViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing)

        // automatically process and predict the image
        val imageFile = intent.getSerializableExtra(EXTRA_IMAGE) as File
        processingViewModel.predictImage(this, imageFile)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    companion object {
        const val EXTRA_IMAGE = "extra_image"
    }
}