package com.bangkit.alpaca.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.alpaca.databinding.ItemTextSpeechBinding

class SentencesListAdapter :
    ListAdapter<String, SentencesListAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(val binding: ItemTextSpeechBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sentence: String?) {
            binding.tvSentence.text = sentence
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemTextSpeechBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val sentence = getItem(position)
        holder.bind(sentence)
        holder.binding.btnPlaySentence.setOnClickListener {
            onItemClickCallback.onItemClicked(sentence, holder.binding.btnPlaySentence)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(sentence: String, btn: ImageButton)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

            }
    }
}