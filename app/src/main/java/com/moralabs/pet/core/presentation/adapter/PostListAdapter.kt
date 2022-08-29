package com.moralabs.pet.core.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.databinding.*

class PostListAdapter(
    private val onRowClick: (post: PostDto) -> Unit
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostListViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostListViewHolder(private val context: Context, private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onRowClick.invoke(getItem(bindingAdapterPosition))
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(pet: PostDto) {

            binding.username.text = pet.user?.userName.toString()
            binding.location.text = pet.content?.location?.latitude.toString()
            binding.postText.text = pet.content?.text.toString()
            binding.likeCount.text = pet.likeCount.toString()
            binding.commentCount.text = pet.commentCount.toString()
            binding.userPhoto.loadImage(pet.user?.image)
            binding.postImage.loadImage(pet.content?.media?.get(0))

            when (pet.content?.type) {
                0 -> {
                  //  binding.postType.visibility = View.GONE
                    binding.postContentLinear.visibility = View.VISIBLE
                    binding.postInfo.visibility = View.VISIBLE
                    binding.postContent2Linear.visibility = View.GONE
                    binding.post2Info.visibility = View.GONE
                    binding.postIcon.setImageResource(R.drawable.ic_qna)
                    binding.postTypeText.text = context.getString(R.string.qna)
                }
                1 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_qna)
                    binding.postTypeText.text = context.getString(R.string.qna)
                    binding.postContentLinear.visibility = View.VISIBLE
                    binding.postInfo.visibility = View.VISIBLE
                    binding.postContent2Linear.visibility = View.GONE
                    binding.post2Info.visibility = View.GONE
                }
                2 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_partner)
                    binding.postTypeText.text = context.getString(R.string.findPartner)
                    binding.postContentLinear.visibility = View.GONE
                    binding.postInfo.visibility = View.GONE
                    binding.postContent2Linear.visibility = View.VISIBLE
                    binding.post2Info.visibility = View.VISIBLE
                }
                3 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_adoption)
                    binding.postTypeText.text = context.getString(R.string.adoption)
                    binding.postContentLinear.visibility = View.GONE
                    binding.postInfo.visibility = View.GONE
                    binding.postContent2Linear.visibility = View.VISIBLE
                    binding.post2Info.visibility = View.VISIBLE

                }else -> 0
            }
        }
    }
}