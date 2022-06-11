package com.bangkit.alpaca.ui.reading

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ActivityReadingBinding
import com.bangkit.alpaca.model.Story
import com.bangkit.alpaca.ui.customization.CustomizationActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadingActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener, View.OnTouchListener {

    private lateinit var binding: ActivityReadingBinding
    private val readingViewModel: ReadingViewModel by viewModels()
    private val arg: ReadingActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupStory()
        setupAction()
        setupCustomization()
    }

    private fun setupCustomization() {
        readingViewModel.getTextBackgroundPreference()
            .observe(this@ReadingActivity) { backgroundColor ->
                val selectedColor = when (backgroundColor) {
                    0 -> getColor(R.color.bg_white)
                    1 -> getColor(R.color.bg_warm)
                    2 -> getColor(R.color.bg_blue)
                    3 -> getColor(R.color.bg_dark)
                    else -> getColor(R.color.bg_black)
                }

                binding.apply {
                    if (backgroundColor > 2) {
                        tvBodyReading.setTextColor(getColor(R.color.white))
                        tvEndOfStory.setTextColor(getColor(R.color.white))
                        tvTitle.setTextColor(getColor(R.color.white))

                        with(collapsingReading) {
                            setCollapsedTitleTextColor(getColor(R.color.white))
                            setExpandedTitleColor(getColor(R.color.white))
                            setCollapsedTitleTextColor(getColor(R.color.white))
                        }

                        with(toolbarReading) {
                            setNavigationIconTint(getColor(R.color.white))
                            setTitleTextColor(getColor(R.color.white))
                            menu.getItem(1).setIcon(R.drawable.ic_play_all_white)
                            overflowIcon =
                                ContextCompat.getDrawable(baseContext, R.drawable.ic_option_white)
                        }
                    } else {
                        tvBodyReading.setTextColor(getColor(R.color.black))
                        tvEndOfStory.setTextColor(getColor(R.color.black))
                        tvTitle.setTextColor(getColor(R.color.black))

                        with(collapsingReading) {
                            setCollapsedTitleTextColor(getColor(R.color.black))
                            setExpandedTitleColor(getColor(R.color.black))
                            setCollapsedTitleTextColor(getColor(R.color.black))
                        }

                        with(toolbarReading) {
                            toolbarReading.setNavigationIconTint(getColor(R.color.black))
                            setTitleTextColor(getColor(R.color.black))
                            menu.getItem(1).setIcon(R.drawable.ic_play_all_black)
                            overflowIcon =
                                ContextCompat.getDrawable(baseContext, R.drawable.ic_option_black)
                        }
                    }

                    collapsingReading.setContentScrimColor(selectedColor)
                    root.setBackgroundColor(selectedColor)
                    titleBackground.setBackgroundColor(selectedColor)
                    toolbarReading.setBackgroundColor(selectedColor)
                }
            }

        readingViewModel.getLineSpacingPreference().observe(this) { lineSpacing ->
            binding.tvBodyReading.letterSpacing = (lineSpacing * 0.05).toFloat()
        }

        readingViewModel.getLineHeightPreference().observe(this) { lineHeight ->
            binding.tvBodyReading.setLineSpacing(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    (lineHeight * 8).toFloat(),
                    resources.displayMetrics
                ), 1f
            )
        }

        readingViewModel.getTextAlignmentPreference().observe(this) { alignmentType ->
            binding.tvBodyReading.gravity = alignmentType
        }

        readingViewModel.getTextSizePreference().observe(this) { textSize ->
            binding.tvBodyReading.textSize = textSize.toFloat()
        }
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
                .into(binding.ivCover)
        }
        binding.toolbarReading.title = story?.title
        binding.tvTitle.text = story?.title
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

            R.id.reading_customization_text -> {
                val customizationIntent = Intent(this, CustomizationActivity::class.java)
                startActivity(customizationIntent)
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

        if (word[startIndex] == ' ') {
            startIndex++
        }

        try {
            var first = word[startIndex]
            while (first == '.' || first == ',' ||
                first == '?' || first == '!' ||
                first == ':' || first == ';' ||
                first == '"' || first == '\''
            ) {
                startIndex++
                first = word[startIndex]
            }
        } catch (e: StringIndexOutOfBoundsException) {
        }

        try {
            while (word[endIndex] != ' ' && word[endIndex] != '\n') {
                endIndex++
            }
        } catch (e: StringIndexOutOfBoundsException) {
            endIndex = word.length
        }

        try {
            var last = word[endIndex - 1]
            while (last == '.' || last == ',' ||
                last == '?' || last == '!' ||
                last == ':' || last == ';' ||
                last == '"' || last == '\''
            ) {
                endIndex--
                last = word[endIndex - 1]
            }
        } catch (e: StringIndexOutOfBoundsException) {
        }

        return word.substring(startIndex, endIndex)
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}