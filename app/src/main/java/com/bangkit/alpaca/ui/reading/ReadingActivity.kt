package com.bangkit.alpaca.ui.reading

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.navArgs
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ActivityReadingBinding
import com.bumptech.glide.Glide

class ReadingActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private lateinit var binding: ActivityReadingBinding
    private val arg: ReadingActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupStory()
        setupAction()
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

    private fun setupAction() {
        binding.toolbarReading.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.reading_speech_play_all -> {
                val story = arg.story
                val bundle = Bundle()
                bundle.putStringArrayList(
                    BottomSheetPlaySpeechAll.EXTRA_SENTENCES,
                    separateStoryToSentence(story.body)
                )

                val modalBottomSheetPlayAllBinding = BottomSheetPlaySpeechAll()
                modalBottomSheetPlayAllBinding.arguments = bundle
                modalBottomSheetPlayAllBinding.show(
                    supportFragmentManager,
                    BottomSheetPlaySpeechAll::class.java.simpleName
                )
                true
            }
            else -> false
        }
    }

    private fun separateStoryToSentence(story: String): ArrayList<String> {
        val listSentence = ArrayList<String>()

        val sentences = story.split(". ")
        for (sentence in sentences) {
            listSentence.add(sentence.trim())
        }
        return listSentence
    }
}