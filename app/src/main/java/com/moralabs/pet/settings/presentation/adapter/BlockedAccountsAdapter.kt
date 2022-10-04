package com.moralabs.pet.settings.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
import com.moralabs.pet.databinding.ItemBlockedAccountsBinding
import com.moralabs.pet.settings.data.remote.dto.BlockedDto

class BlockedAccountsAdapter(

    private val buttonClick: (item: BlockedDto) -> Unit,

) : ListAdapter<BlockedDto, BlockedAccountsAdapter.BlockedAccountsViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BlockedDto>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: BlockedDto, newItem: BlockedDto): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: BlockedDto, newItem: BlockedDto): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockedAccountsViewHolder {
        val binding = ItemBlockedAccountsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BlockedAccountsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlockedAccountsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BlockedAccountsViewHolder(private val binding: ItemBlockedAccountsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.blockUnBlockButton.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    buttonClick.invoke(getItem(bindingAdapterPosition))
                    notifyDataSetChanged()
                }
            }
        }

        fun bind(item: BlockedDto) {
            binding.userPhoto.loadImageWithPlaceholder(item.media?.url)
            binding.username.text = item.fullName
            binding.blockUnBlockButton.isSelected = item.selected
        }
    }
}
