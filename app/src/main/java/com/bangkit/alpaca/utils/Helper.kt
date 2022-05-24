package com.bangkit.alpaca.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.bangkit.alpaca.R

open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

object LoadingDialog {
    private var dialog: Dialog? = null

    fun displayLoading(
        context: Context,
        cancelable: Boolean
    ) {
        dialog = Dialog(context)

        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.view_loading_screen)
            setCancelable(cancelable)
        }

        try {
            dialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideLoading() {
        try {
            dialog?.dismiss()
            dialog = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}