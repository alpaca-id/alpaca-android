package com.bangkit.alpaca.utils

import android.animation.ObjectAnimator
import android.view.View
import com.bangkit.alpaca.data.local.entity.StoryEntity
import com.bangkit.alpaca.model.Story
import java.text.DateFormat
import java.util.*

/**
 * Convert the Unix Timestamp to formatted date in String
 *
 * @return String
 */
fun Long.toFormattedString(): String {
    val date = Date(this)
    return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale("id", "ID")).format(date)
}

/**
 * Animate View's visibility by change the alpha value
 *
 * @param isVisible: Boolean
 * @param duration: Long
 */
fun View.animateVisibility(isVisible: Boolean, duration: Long = 400) {
    ObjectAnimator.ofFloat(this, View.ALPHA, if (isVisible) 1f else 0f).apply {
        this.duration = duration
        start()
    }
}

/**
 * Convert StoryEntity object to Story object
 */
fun StoryEntity.toStory(): Story {
    return Story(
        id = this.id,
        title = this.title,
        body = this.body,
        coverPath = this.coverPath,
        authorName = this.authorName,
        createdAt = this.createdAt
    )
}

/**
 * Convert Story object to StoryEntity object
 */
fun Story.toStoryEntity(): StoryEntity {
    return StoryEntity(
        id = this.id,
        title = this.title,
        body = this.body,
        coverPath = this.coverPath,
        authorName = this.authorName,
        createdAt = this.createdAt
    )
}