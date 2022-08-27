package com.moralabs.pet.core.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.databinding.*

class PostListAdapter(
    private val onRowClick: (position: Int , post: PostDto) -> Unit
) : ListAdapter<PostDto, PostListAdapter.PostListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostDto>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: PostDto, newItem: PostDto): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: PostDto, newItem: PostDto): Boolean {
                return oldItem == newItem
            }
        }

        private const val TYPE_POST = 0
        private const val TYPE_QNA = 1
        private const val TYPE_FINDPARTNER = 2
        private const val TYPE_ADOPTION = 3

    }

    fun setItems(pet: List<PostDto>) {
        submitList(pet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostListViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.userInfoLinear.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onRowClick.invoke(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(pet: PostDto) {


        }
    }
}