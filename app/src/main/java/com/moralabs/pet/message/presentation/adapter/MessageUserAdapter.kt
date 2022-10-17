package com.moralabs.pet.message.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.BR
import com.moralabs.pet.core.data.remote.dto.SimpleDto
import com.moralabs.pet.databinding.UiItemBaseListEmptyBinding
import com.moralabs.pet.petProfile.data.remote.dto.PetDto

class MessageUserAdapter<Dto, Binding : ViewDataBinding>(
    private val layoutId: Int,
    private val modelId: Int? = null,
    private val onRowClick: ((result: Dto) -> Unit)? = null,
    private val emptyString: String? = null
) :
    ListAdapter<Dto, MessageUserAdapter.ViewHolder<Binding>>(object :
        DiffUtil.ItemCallback<Dto>() {

        override fun areItemsTheSame(oldItem: Dto, newItem: Dto): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Dto, newItem: Dto): Boolean {
            return (oldItem as PetDto).selected == (newItem as PetDto).selected
        }
    }) {

    private val EMPTY_TYPE = 1
    private val DTO_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        EMPTY_TYPE -> ViewHolder.EmptyViewHolder(
            UiItemBaseListEmptyBinding.inflate(LayoutInflater.from(parent.context)),
            this
        )
        else -> ViewHolder.DtoViewHolder<Binding>(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false),
            this
        )
    }

    override fun onBindViewHolder(holder: ViewHolder<Binding>, position: Int) {
        val item = getItem(position)

        when (holder) {
            is ViewHolder.DtoViewHolder<*> -> {
                modelId?.let {
                    if (item is SimpleDto<*>) {
                        holder.binding.setVariable(it, item.value)
                    } else {
                        holder.binding.setVariable(it, item)
                    }
                }
            }
            else -> {}
        }

    }

    override fun submitList(list: List<Dto>?) {

        if (list.isNullOrEmpty() && emptyString.isNullOrEmpty().not()) {
            super.submitList(listOf(null))
        } else {
            super.submitList(list)
        }
    }

    sealed class ViewHolder<Binding : ViewDataBinding>(
        binding: Binding,
        listAdapter: MessageUserAdapter<*, *>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        class DtoViewHolder<Binding : ViewDataBinding>(
            val binding: Binding,
            listAdapter: MessageUserAdapter<*, *>
        ) : ViewHolder<Binding>(binding, listAdapter)

        class EmptyViewHolder<Binding : ViewDataBinding>(
            val binding: UiItemBaseListEmptyBinding,
            listAdapter: MessageUserAdapter<*, *>
        ) :
            ViewHolder<Binding>(binding as Binding, listAdapter)

        init {
            when (binding) {
                is UiItemBaseListEmptyBinding -> binding.setVariable(BR.item, listAdapter.emptyString)
                else -> {
                    binding.root.setOnClickListener {
                        if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                            listAdapter.selectItem(bindingAdapterPosition)
                        }
                    }
                }
            }
        }
    }

    fun selectItem(position: Int) {
        onRowClick?.invoke(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {

        if (getItem(position) == null) {
            return EMPTY_TYPE
        }

        return DTO_TYPE
    }
}