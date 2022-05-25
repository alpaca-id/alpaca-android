package com.bangkit.alpaca.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
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

fun TextInputLayout.showError(isError: Boolean, message: String? = null) {
    if (isError) {
        isErrorEnabled = false
        error = null
        isErrorEnabled = true
        error = message
    } else {
        isErrorEnabled = false
        error = null
    }
}

fun String.showToastMessage(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}