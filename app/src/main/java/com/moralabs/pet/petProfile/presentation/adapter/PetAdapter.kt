package com.moralabs.pet.petProfile.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.BR
import com.moralabs.pet.databinding.ItemAddPetAttributeBinding
import com.moralabs.pet.databinding.ItemAddPetAttributeListBinding
import com.moralabs.pet.databinding.ItemAddPetPhotoBinding
import com.moralabs.pet.petProfile.data.remote.dto.ChoicesDto
import com.moralabs.pet.petProfile.presentation.model.AttributeUiDto
import com.moralabs.pet.petProfile.presentation.model.AttributeUiType

class PetAdapter(
    private val context: Context,
    private val onPhotoClicked: ((result: AttributeUiDto) -> Unit)? = null,
) :
    ListAdapter<AttributeUiDto, PetAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<AttributeUiDto>() {

        override fun areItemsTheSame(oldItem: AttributeUiDto, newItem: AttributeUiDto) =
            oldItem.uiType == newItem.uiType && oldItem.attributeDto?.id == newItem.attributeDto?.id

        override fun areContentsTheSame(oldItem: AttributeUiDto, newItem: AttributeUiDto) = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        AttributeUiType.PHOTO.value -> ViewHolder.PetAttributePhotoViewHolder(
            ItemAddPetPhotoBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ),
            this
        )
        AttributeUiType.NAME.value -> ViewHolder.PetAttributeNameViewHolder(
            ItemAddPetAttributeBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ),
            this
        )
        AttributeUiType.ATTRIBUTE_LIST.value -> ViewHolder.PetAttributeListViewHolder(
            ItemAddPetAttributeListBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ),
            this
        )
        else -> ViewHolder.PetAttributeViewHolder(
            ItemAddPetAttributeBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ),
            this
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.setVariable(BR.item, getItem(position))

        if (holder is ViewHolder.PetAttributeListViewHolder) {

            (holder.binding as? ItemAddPetAttributeListBinding)?.choices?.setAdapter(object :
                ArrayAdapter<ChoicesDto>(
                    context,
                    android.R.layout.simple_list_item_1,
                    getItem(position).attributeDto?.choices ?: listOf()
                ) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)

                    (view as? TextView)?.text = getItem(position)?.choice

                    return view
                }
            })

            getItem(position).choice?.let {
                (holder.binding as? ItemAddPetAttributeListBinding)?.choices?.setText(it, false)
            }
        }
    }

    sealed class ViewHolder(
        val binding: ViewDataBinding,
        private val petAdapter: PetAdapter
    ) :
        RecyclerView.ViewHolder(binding.root) {

        class PetAttributePhotoViewHolder(
            binding: ItemAddPetPhotoBinding,
            petAdapter: PetAdapter
        ) : ViewHolder(binding, petAdapter)

        class PetAttributeNameViewHolder(
            binding: ItemAddPetAttributeBinding,
            petAdapter: PetAdapter
        ) : ViewHolder(binding, petAdapter)

        class PetAttributeViewHolder(
            binding: ItemAddPetAttributeBinding,
            petAdapter: PetAdapter
        ) : ViewHolder(binding, petAdapter)

        class PetAttributeListViewHolder(
            binding: ItemAddPetAttributeListBinding,
            petAdapter: PetAdapter
        ) : ViewHolder(binding, petAdapter)

        init {
            when (this) {
                is PetAttributePhotoViewHolder -> {
                    binding.root.setOnClickListener {
                        if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                            petAdapter.onPhotoClicked(bindingAdapterPosition)
                        }
                    }
                }
                is PetAttributeNameViewHolder -> {
                    (binding as? ItemAddPetAttributeBinding)?.choice?.addTextChangedListener {
                        if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                            petAdapter.setChoice(bindingAdapterPosition, it.toString())
                        }
                    }
                }
                is PetAttributeViewHolder -> {
                    (binding as? ItemAddPetAttributeBinding)?.choice?.addTextChangedListener {
                        if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                            petAdapter.setChoice(bindingAdapterPosition, it.toString())
                        }
                    }
                }
                is PetAttributeListViewHolder -> {
                    (binding as? ItemAddPetAttributeListBinding)?.choices?.setOnItemClickListener { _, _, position, _ ->
                        if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                            (binding as? ItemAddPetAttributeListBinding)?.choices?.setText(
                                petAdapter.setChoicePosition(bindingAdapterPosition, position), false
                            )
                        }
                    }
                }
            }

        }
    }

    override fun getItemViewType(position: Int) = getItem(position).uiType.value

    fun setChoice(position: Int, choice: String) {
        getItem(position).choice = choice
    }

    fun setChoicePosition(position: Int, choice: Int): String? {
        getItem(position).choice = getItem(position).attributeDto?.choices?.getOrNull(choice)?.choice
        getItem(position).choiceId = getItem(position).attributeDto?.choices?.getOrNull(choice)?.id

        return getItem(position).attributeDto?.choices?.getOrNull(choice)?.choice
    }

    fun onPhotoClicked(position: Int) {
        onPhotoClicked?.invoke(getItem(position))
    }
}