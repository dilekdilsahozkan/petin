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
import com.moralabs.pet.databinding.ItemPostBinding

class PostListAdapter(
    private val onOfferClick: ((post: PostDto) -> Unit)? = null,
    private val onPetProfile: ((post: PostDto) -> Unit)? = null,
    private val onLikeClick: ((post: PostDto) -> Unit)? = null,
    private val onCommentClick: ((post: PostDto) -> Unit)? = null,
    private val onOfferUserClick: ((post: PostDto) -> Unit)? = null,
    private val onUserPhotoClick: ((post: PostDto) -> Unit)? = null,
    private val onPostSettingClick: ((user: PostDto) -> Unit)? = null
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
                    onCommentClick?.invoke(getItem(bindingAdapterPosition))
                }
            }

            binding.petImage.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onPetProfile?.invoke(getItem(bindingAdapterPosition))
                    notifyDataSetChanged()
                }
            }

            binding.likeIcon.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onLikeClick?.invoke(getItem(bindingAdapterPosition))
                    notifyDataSetChanged()
                }
            }

            binding.offerButton.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onOfferClick?.invoke(getItem(bindingAdapterPosition))
                }
            }

            binding.postOfferLinear.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onOfferUserClick?.invoke(getItem(bindingAdapterPosition))
                }
            }

            binding.userPhoto.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onUserPhotoClick?.invoke(getItem(bindingAdapterPosition))
                }
            }

            binding.userInfoPart.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onUserPhotoClick?.invoke(getItem(bindingAdapterPosition))
                }
            }

            binding.postSetting.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onPostSettingClick?.invoke(getItem(bindingAdapterPosition))
                    notifyDataSetChanged()
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(post: PostDto) {
            binding.username.text = post.user?.userName.toString()
            binding.userPhoto.loadImageWithPlaceholder(post.user?.media?.url)
            binding.postText.text = post.content?.text.toString()
            binding.post2Text.text = post.content?.text.toString()
            binding.likeCount.text = post.likeCount.toString()
            binding.commentCount.text = post.commentCount.toString()
            binding.offerCount.text = post.offerCount.toString()
            binding.postReleaseTime.text = post.dateTime.toFullDate(context)
            binding.post2ReleaseTime.text = post.dateTime.toFullDate(context)

            binding.petName.text = post.content?.pet?.name
            binding.petKind.text = post.content?.pet?.petAttributes?.filter { it.attributeType == 6 }?.getOrNull(0)?.choice
            binding.petLocation.text = post.content?.pet?.petAttributes?.filter { it.attributeType == 5 }?.getOrNull(0)?.choice
            binding.petGender.text = post.content?.pet?.petAttributes?.filter { it.attributeType == 8 }?.getOrNull(0)?.choice

            if (post.isPostLikedByUser == true) {
                binding.likeIcon.setImageResource(R.drawable.ic_like_orange)
            } else {
                binding.likeIcon.setImageResource(R.drawable.ic_like)
            }

            if (post.isPostOwnedByUser == true) {
                binding.postSetting.visibility = View.VISIBLE
                binding.offerButton.visibility = View.GONE
            } else {
                binding.postSetting.visibility = View.GONE
                binding.offerButton.visibility = View.VISIBLE
            }

            if (post.content?.pet?.media?.url.isNullOrEmpty()) {
                binding.petImage.visibility = View.GONE
            } else {
                binding.petImage.visibility = View.VISIBLE
                binding.petImage.loadImage(post.content?.pet?.media?.url)
            }

            if (post.content?.location?.city.isNullOrEmpty()) {
                binding.location.visibility = View.GONE
            } else {
                binding.location.visibility = View.VISIBLE
                binding.location.text = post.content?.location?.city.toString()
            }

            if (post.content?.media.isNullOrEmpty()) {
                binding.postImage.visibility = View.GONE
            } else {
                binding.postImage.visibility = View.VISIBLE
                binding.postImage.loadImage(post.content?.media?.get(0)?.url)
            }

            when (post.content?.type) {
                0 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_post)
                    binding.postTypeText.text = context.getString(R.string.post)
                    binding.postContentLinear.visibility = View.VISIBLE
                    binding.postContent2Linear.visibility = View.GONE
                    binding.empty2.visibility = View.GONE

                    binding.empty.visibility =
                        if (bindingAdapterPosition == currentList.size - 1) View.VISIBLE else View.GONE
                }
                1 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_qna)
                    binding.postTypeText.text = context.getString(R.string.qna)
                    binding.postContentLinear.visibility = View.VISIBLE
                    binding.postContent2Linear.visibility = View.GONE
                    binding.empty2.visibility = View.GONE

                    binding.empty.visibility =
                        if (bindingAdapterPosition == currentList.size - 1) View.VISIBLE else View.GONE
                }
                2 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_partner)
                    binding.postTypeText.text = context.getString(R.string.findPartner)
                    binding.postContentLinear.visibility = View.GONE
                    binding.postContent2Linear.visibility = View.VISIBLE
                    binding.empty.visibility = View.GONE

                    binding.empty2.visibility =
                        if (bindingAdapterPosition == currentList.size - 1) View.VISIBLE else View.GONE
                }
                3 -> {
                    binding.postIcon.setImageResource(R.drawable.ic_adoption)
                    binding.postTypeText.text = context.getString(R.string.adoption)
                    binding.postContentLinear.visibility = View.GONE
                    binding.postContent2Linear.visibility = View.VISIBLE
                    binding.empty.visibility = View.GONE

                    binding.empty2.visibility =
                        if (bindingAdapterPosition == currentList.size - 1) View.VISIBLE else View.GONE
                }
            }
        }
    }
}