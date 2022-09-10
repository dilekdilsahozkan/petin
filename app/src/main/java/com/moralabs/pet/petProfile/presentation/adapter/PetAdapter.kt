package com.moralabs.pet.petProfile.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.databinding.ItemPetCardBinding
import com.moralabs.pet.petProfile.data.remote.dto.PetDto

class PetAdapter(
    private val onDeleteClick: (post: PetDto) -> Unit,
    private val onPetClick: (post: PetDto) -> Unit,
) : ListAdapter<PetDto, PetAdapter.PetViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PetDto>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: PetDto, newItem: PetDto): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: PetDto, newItem: PetDto): Boolean {
                return oldItem == newItem
            }
        }
    }

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PetViewHolder(private val context: Context, val binding: ItemPetCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.deleteCard.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onDeleteClick.invoke(getItem(bindingAdapterPosition))
                }
            }

            binding.petCard.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onPetClick.invoke(getItem(bindingAdapterPosition))
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(pet: PetDto) {

            binding.offerImage.loadImage(pet.media?.url)

        }
    }
}