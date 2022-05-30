package com.bangkit.alpaca.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.alpaca.databinding.CardCollectionItemBinding
import com.bangkit.alpaca.model.Story
import com.bangkit.alpaca.utils.toFormattedString
import com.bumptech.glide.Glide

class CollectionListAdapter : ListAdapter<Story, CollectionListAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(private val binding: CardCollectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: Story) {
            binding.tvCollectionItemTitle.text = story.title
            binding.tvCollectionItemTitleCover.text = story.title
            binding.tvCollectionItemStatus.text =
                story.authorName ?: story.createdAt.toFormattedString()

            if (story.coverPath != null) {
                Glide
                    .with(binding.root.context)
                    .load(story.coverPath)
                    .centerCrop()
                    .into(binding.ivCollectionItemCover)

                binding.tvCollectionItemTitleCover.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardCollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(story) }
    }

    /**
     * Set an item click callback
     *
     * @param onItemClickCallback   object that implements onItemClickCallback
     * @return Unit
     */
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(story: Story)
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