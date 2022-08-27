package com.moralabs.pet.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.databinding.ItemAdoptionBinding
import com.moralabs.pet.databinding.ItemFindPartnerBinding
import com.moralabs.pet.databinding.ItemPostBinding
import com.moralabs.pet.databinding.ItemQnaBinding

class PostListAdapter(private var mList: List<Any>)  : RecyclerView.Adapter<PostListAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListAdapterViewHolder {

        when(viewType){
            R.layout.item_post -> {
                val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PostListAdapterViewHolder.PostViewHolder(binding)
            }
            R.layout.item_qna -> {
                val binding = ItemQnaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PostListAdapterViewHolder.QnAViewHolder(binding)
            }
            R.layout.item_find_partner -> {
                val binding = ItemFindPartnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PostListAdapterViewHolder.FindPartnerViewHolder(binding)
            }
            R.layout.item_adoption -> {
                val binding = ItemAdoptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PostListAdapterViewHolder.AdoptionViewHolder(binding)
            }
        }

        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostListAdapterViewHolder.PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostListAdapterViewHolder, position: Int) {
        when(holder){
            is PostListAdapterViewHolder.PostViewHolder -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {

        when(mList[position]){
            is PostDto -> return R.layout.item_post
            is PostDto -> return R.layout.item_qna
            is PostDto -> return R.layout.item_find_partner
            is PostDto -> return R.layout.item_adoption
        }
        return R.layout.item_post
    }


}