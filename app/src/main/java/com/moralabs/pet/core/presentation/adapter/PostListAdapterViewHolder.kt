package com.moralabs.pet.core.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.databinding.*

sealed class PostListAdapterViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {

    class PostViewHolder(val postItem: ItemPostBinding) : PostListAdapterViewHolder(postItem.root)
    class QnAViewHolder(val qnaItem: ItemQnaBinding) : PostListAdapterViewHolder(qnaItem.root)
    class FindPartnerViewHolder(val ItemFind: ItemFindPartnerBinding) : PostListAdapterViewHolder(ItemFind.root)
    class AdoptionViewHolder(val adoptionItem: ItemAdoptionBinding) : PostListAdapterViewHolder(adoptionItem.root)
}
