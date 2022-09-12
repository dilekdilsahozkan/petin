package com.moralabs.pet.core.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.extension.toFullDate
import com.moralabs.pet.databinding.*

class PostListAdapter(
    private val onOfferClick: (post: PostDto) -> Unit,
    private val onLikeClick: (post: PostDto) -> Unit,
    private val onCommentClick: (post: PostDto) -> Unit,
    private val onOfferUserClick: (post: PostDto) -> Unit,
    private val onUserPhotoClick: (user: PostDto) -> Unit
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

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostListViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostListViewHolder(private val context: Context, val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.postCommentLinear.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onCommentClick.invoke(getItem(bindingAdapterPosition))
                }
            }

            binding.likeIcon.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onLikeClick.invoke(getItem(bindingAdapterPosition))
                }
            }

            binding.offerButton.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onOfferClick.invoke(getItem(bindingAdapterPosition))
                }
            }

            binding.postOfferLinear.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onOfferUserClick.invoke(getItem(bindingAdapterPosition))
                }
            }
            binding.userPhoto.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onUserPhotoClick.invoke(getItem(bindingAdapterPosition))
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(pet: PostDto) {

            binding.username.text = pet.user?.userName.toString()
            binding.userPhoto.loadImage(pet.user?.media?.url)
            binding.postText.text = pet.content?.text.toString()
            binding.post2Text.text = pet.content?.text.toString()
            binding.likeCount.text = pet.likeCount.toString()
            binding.commentCount.text = pet.commentCount.toString()
            binding.offerCount.text = pet.offerCount.toString()
            binding.postReleaseTime.text = pet.dateTime.toFullDate(context)
            binding.post2ReleaseTime.text = pet.dateTime.toFullDate(context)
            binding.petName.text = pet.content?.pet?.name

            if (pet.content?.pet?.media?.url.isNullOrEmpty()) {
                binding.petImage.visibility = View.GONE
            } else {
                binding.petImage.visibility = View.VISIBLE
                binding.petImage.loadImage(pet.content?.pet?.media?.url)
            }

            if (pet.content?.location?.city.isNullOrEmpty()) {
                binding.location.visibility = View.GONE
            } else {
                binding.location.visibility = View.VISIBLE
                binding.location.text = pet.content?.location?.city.toString()
            }

            if (pet.content?.media.isNullOrEmpty()) {
                binding.postImage.visibility = View.GONE
            } else {
                binding.postImage.visibility = View.VISIBLE
                binding.postImage.loadImage(pet.content?.media?.get(0)?.url)
            }

            when (pet.content?.type) {
                0 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_post)
                    binding.postTypeText.text = context.getString(R.string.post)
                    binding.postContentLinear.visibility = View.VISIBLE
                    binding.postContent2Linear.visibility = View.GONE
                    binding.empty2.visibility = View.GONE

                    binding.empty.visibility = if (bindingAdapterPosition == currentList.size - 1) View.VISIBLE else View.GONE
                }
                1 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_qna)
                    binding.postTypeText.text = context.getString(R.string.qna)
                    binding.postContentLinear.visibility = View.VISIBLE
                    binding.postContent2Linear.visibility = View.GONE
                    binding.empty2.visibility = View.GONE

                    binding.empty.visibility = if (bindingAdapterPosition == currentList.size - 1) View.VISIBLE else View.GONE
                }
                2 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_partner)
                    binding.postTypeText.text = context.getString(R.string.findPartner)
                    binding.postContentLinear.visibility = View.GONE
                    binding.postContent2Linear.visibility = View.VISIBLE
                    binding.empty.visibility = View.GONE

                    binding.empty2.visibility = if (bindingAdapterPosition == currentList.size - 1) View.VISIBLE else View.GONE
                }
                3 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_adoption)
                    binding.postTypeText.text = context.getString(R.string.adoption)
                    binding.postContentLinear.visibility = View.GONE
                    binding.postContent2Linear.visibility = View.VISIBLE
                    binding.empty.visibility = View.GONE

                    binding.empty2.visibility = if (bindingAdapterPosition == currentList.size - 1) View.VISIBLE else View.GONE
                }
            }
        }
    }
}