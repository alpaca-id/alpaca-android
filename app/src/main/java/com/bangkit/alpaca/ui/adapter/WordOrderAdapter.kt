package com.bangkit.alpaca.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ItemLevelBinding
import com.bangkit.alpaca.model.Level

class WordOrderAdapter : ListAdapter<Level, WordOrderAdapter.ListViewAdapter>(DIFF_CALLBACK) {

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
        val level = getItem(position)
        holder.bind(level)
        holder.binding.cardLevel.setOnClickListener {
            onItemClickCallback.onItemClicked(level)
        }
    }

    class ListViewAdapter(val binding: ItemLevelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(level: Level) {
            binding.tvWordOrderLevel.text = level.level.toString()
            if (level.isComplete) {
                binding.cardLevel.setBackgroundColor(itemView.context.getColor(R.color.yellow_500))
            } else {
                binding.cardLevel.background =
                    getDrawable(itemView.context, R.drawable.bg_incomplete)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Level> =
            object : DiffUtil.ItemCallback<Level>() {
                override fun areItemsTheSame(oldItem: Level, newItem: Level): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: Level, newItem: Level): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }

    interface OnItemClickCallback {
        fun onItemClicked(level: Level)
    }
}