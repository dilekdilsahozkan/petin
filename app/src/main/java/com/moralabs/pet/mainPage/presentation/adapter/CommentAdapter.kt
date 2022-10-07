package com.moralabs.pet.mainPage.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.core.data.remote.dto.CommentsDto
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
import com.moralabs.pet.core.presentation.extension.toFullDate
import com.moralabs.pet.databinding.ItemUserCommentBinding

class CommentAdapter(
    private val onButtonClick: ((item: CommentsDto) -> Unit)? = null,
    private val onUserPhotoClick: ((item: CommentsDto) -> Unit)? = null,
) : ListAdapter<CommentsDto, CommentAdapter.CommentViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommentsDto>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: CommentsDto, newItem: CommentsDto): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: CommentsDto, newItem: CommentsDto): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding =
            ItemUserCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(parent.context,binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CommentViewHolder(private val context: Context, private val binding: ItemUserCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.commentSetting.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onButtonClick?.invoke(getItem(bindingAdapterPosition))
                    notifyDataSetChanged()
                }
            }
            binding.userPhoto.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onUserPhotoClick?.invoke(getItem(bindingAdapterPosition))
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(comment: CommentsDto) {
            binding.userPhoto.loadImageWithPlaceholder(comment.user?.media?.url)
            binding.username.text = comment.user?.userName
            binding.commentText.text = comment.text
            binding.commentDate.text = comment.dateTime.toFullDate(context)

            if (comment.isCommentOwnedByUser == true) {
                binding.commentSetting.visibility = View.VISIBLE
            } else {
                binding.commentSetting.visibility = View.GONE
            }
        }
    }
}
