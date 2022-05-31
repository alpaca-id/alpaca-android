package com.bangkit.alpaca.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ItemTextSpeechBinding
import com.bangkit.alpaca.model.Sentence

class SentencesListAdapter :
    ListAdapter<Sentence, SentencesListAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var lastIndex: Int? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(val binding: ItemTextSpeechBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sentence: Sentence) {
            binding.tvSentence.text = sentence.text
            binding.btnPlaySentence.setImageResource(
                if (sentence.isPlaying) R.drawable.ic_pause_sentence
                else R.drawable.ic_play_sentence
            )
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
            if (lastIndex != holder.adapterPosition) {
                lastIndex?.let { index ->
                    getItem(index).isPlaying = false
                    notifyItemChanged(index)
                }
            }

            sentence.isPlaying = !sentence.isPlaying
            notifyItemChanged(position)

            onItemClickCallback.onItemClicked(sentence.text, holder.binding.btnPlaySentence)
            lastIndex = holder.adapterPosition
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(sentence: String, btn: ImageButton)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Sentence> =
            object : DiffUtil.ItemCallback<Sentence>() {
                override fun areItemsTheSame(oldItem: Sentence, newItem: Sentence): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: Sentence, newItem: Sentence): Boolean {
                    return oldItem.isPlaying == newItem.isPlaying
                }

            }
        private const val TAG = "SentencesListAdapter"
    }
}