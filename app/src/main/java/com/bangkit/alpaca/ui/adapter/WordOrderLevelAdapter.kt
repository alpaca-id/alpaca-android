package com.bangkit.alpaca.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ItemLevelBinding
import com.bangkit.alpaca.model.WordLevel

class WordOrderLevelAdapter :
    ListAdapter<WordLevel, WordOrderLevelAdapter.ListViewAdapter>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setonItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewAdapter {
        val binding = ItemLevelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewAdapter(binding)
    }

    override fun onBindViewHolder(holder: ListViewAdapter, position: Int) {
        val wordLevel = getItem(position)
        holder.bind(wordLevel)
        holder.binding.cardLevel.setOnClickListener {
            onItemClickCallback.onItemClicked(wordLevel)
        }
    }

    class ListViewAdapter(val binding: ItemLevelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wordLevel: WordLevel) {
            binding.tvWordOrderLevel.text = wordLevel.level.toString()
            if (wordLevel.isComplete) {
                binding.cardLevel.setBackgroundColor(itemView.context.getColor(R.color.yellow_500))
            } else {
                binding.cardLevel.background =
                    getDrawable(itemView.context, R.drawable.bg_incomplete)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<WordLevel> =
            object : DiffUtil.ItemCallback<WordLevel>() {
                override fun areItemsTheSame(oldItem: WordLevel, newItem: WordLevel): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: WordLevel, newItem: WordLevel): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }

    interface OnItemClickCallback {
        fun onItemClicked(wordLevel: WordLevel)
    }
}