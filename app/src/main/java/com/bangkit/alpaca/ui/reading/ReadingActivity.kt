package com.bangkit.alpaca.ui.reading

import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.navArgs
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ActivityReadingBinding
import com.bangkit.alpaca.model.Story
import com.bumptech.glide.Glide

class ReadingActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener, View.OnTouchListener {

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
        val story: Story? = try {
            arg.story
        } catch (e: Exception) {
            intent.getParcelableExtra(EXTRA_STORY)
        }

        if (story?.coverPath != null) {
            Glide.with(binding.root)
                .load(story.coverPath)
                .into(binding.imgCoverReading)
        }
        binding.toolbarReading.title = story?.title
        binding.tvBodyReading.text = story?.body


    }

    private fun setupAction() {
        binding.toolbarReading.setOnMenuItemClickListener(this)
        binding.tvBodyReading.setOnTouchListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.reading_speech_play_all -> {
                val story: Story? = try {
                    arg.story
                } catch (e: Exception) {
                    intent.getParcelableExtra(EXTRA_STORY)
                }

                val bundle = Bundle()
                if (story != null) {
                    bundle.putStringArrayList(
                        BottomSheetPlaySpeechAll.EXTRA_SENTENCES,
                        separateStoryToSentence(story.body)
                    )
                }

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

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        view?.performClick()
        return when (motionEvent?.action) {
            MotionEvent.ACTION_UP -> {
                val mOffset =
                    binding.tvBodyReading.getOffsetForPosition(motionEvent.x, motionEvent.y)
                val word = wordSelected(binding.tvBodyReading.text.toString(), mOffset)

                val bundle = Bundle()
                bundle.putString(BottomSheetPlaySpeechWord.EXTRA_WORD, word)

                val modalBottomSheetPlayWordBinding = BottomSheetPlaySpeechWord()
                modalBottomSheetPlayWordBinding.arguments = bundle
                modalBottomSheetPlayWordBinding.show(
                    supportFragmentManager,
                    BottomSheetPlaySpeechWord::class.java.simpleName
                )

                true
            }
            else -> false
        }
    }

    private fun wordSelected(word: String, offset: Int): String {
        var mOffset = offset
        if (word.length == mOffset) {
            mOffset--
        }

        if (word[mOffset] == ' ') {
            mOffset--
        }

        var startIndex = mOffset
        var endIndex = mOffset

        try {
            while (word[startIndex] != ' ' && word[startIndex] != '\n') {
                startIndex--
            }
        } catch (e: StringIndexOutOfBoundsException) {
            startIndex = 0
        }

        try {
            while (word[endIndex] != ' ' && word[endIndex] != '\n') {
                endIndex++
            }
        } catch (e: StringIndexOutOfBoundsException) {
            endIndex = word.length
        }

        val last = word[endIndex - 1]
        if (last == '.' || last == ',' || last == '?' || last == '!' || last == ':' || last == ';') {
            endIndex--
        }
        return word.substring(startIndex, endIndex)
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}