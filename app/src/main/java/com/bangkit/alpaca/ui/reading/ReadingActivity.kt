package com.bangkit.alpaca.ui.reading

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.bangkit.alpaca.databinding.ActivityReadingBinding
import com.bumptech.glide.Glide

class ReadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadingBinding
    private val arg: ReadingActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupStory()
    }

    private fun setupToolbar() {
        binding.toolbarReading.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupStory() {
        val story = arg.story

        if (story.coverPath != null) {
            Glide.with(binding.root)
                .load(story.coverPath)
                .into(binding.imgCoverReading)
        }
        binding.toolbarReading.title = story.title
        binding.tvBodyReading.text = story.body
    }
}