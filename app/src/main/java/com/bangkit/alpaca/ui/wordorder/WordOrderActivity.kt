package com.bangkit.alpaca.ui.wordorder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.alpaca.databinding.ActivityWordOrderBinding
import com.bangkit.alpaca.model.Level
import com.bangkit.alpaca.ui.adapter.WordOrderAdapter
import com.bangkit.alpaca.utils.DataDummy
import com.bangkit.alpaca.utils.showToastMessage

class WordOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordOrderBinding
    private val wordOrderAdapter by lazy { WordOrderAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLevel()
        setupAction()
    }

    private fun setupLevel() {
        wordOrderAdapter.submitList(DataDummy.providesWordOrderLevel())
        binding.rvWordOrder.apply {
            adapter = wordOrderAdapter
            layoutManager = GridLayoutManager(this@WordOrderActivity, 3)
        }
    }

    private fun setupAction() {
        wordOrderAdapter.setonItemClickCallback(object : WordOrderAdapter.OnItemClickCallback {
            override fun onItemClicked(level: Level) {
                level.level.toString().showToastMessage(this@WordOrderActivity)
            }
        })
    }
}