package com.bangkit.alpaca.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ItemLibraryBinding
import com.bangkit.alpaca.model.Story
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class LibraryListAdapter : ListAdapter<Story, LibraryListAdapter.ListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    inner class ListViewHolder(val binding: ItemLibraryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            with(binding) {
                tvItemTitleStory.text = story.title
                tvItemDescStory.text = story.body
                tvItemAuthorStory.text = story.authorName
                Glide.with(itemView)
                    .load(story.coverPath)
                    .apply(RequestOptions().placeholder(R.color.yellow_500))
                    .into(imgItemCoverStory)
            }
        }

    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Story> = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

        }
    }
}