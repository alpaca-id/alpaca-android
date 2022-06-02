package com.bangkit.alpaca.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ItemCharButtonBinding
import com.bangkit.alpaca.model.AnswerButton

class AnswerButtonAdapter :
    ListAdapter<AnswerButton, AnswerButtonAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(val binding: ItemCharButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(answerButton: AnswerButton) {
            binding.apply {
                btnAnswerChar.text = answerButton.char

                if (answerButton.isClicked) {
                    btnAnswerChar.setTextColor(itemView.context.getColor(R.color.black))
                    btnAnswerChar.backgroundTintList =
                        itemView.context.getColorStateList(R.color.white)
                } else {
                    btnAnswerChar.setTextColor(itemView.context.getColor(R.color.white))
                    btnAnswerChar.backgroundTintList =
                        itemView.context.getColorStateList(R.color.purple_500)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemCharButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val answerButton = getItem(position)
        holder.bind(answerButton)
        holder.binding.btnAnswerChar.setOnClickListener {
            answerButton.isClicked = !answerButton.isClicked
            notifyItemChanged(position)
            onItemClickCallback.onItemClicked(answerButton, position)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(answerButton: AnswerButton, position: Int)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<AnswerButton> =
            object : DiffUtil.ItemCallback<AnswerButton>() {
                override fun areItemsTheSame(
                    oldItem: AnswerButton,
                    newItem: AnswerButton
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: AnswerButton,
                    newItem: AnswerButton
                ): Boolean {
                    return oldItem.char == newItem.char
                }

            }
    }
}