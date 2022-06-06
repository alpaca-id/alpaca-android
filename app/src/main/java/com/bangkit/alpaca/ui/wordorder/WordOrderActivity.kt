package com.bangkit.alpaca.ui.wordorder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.alpaca.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_order)
    }
}