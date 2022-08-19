package com.moralabs.pet.mainPage.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.mainPage.data.remote.dto.ContentTypeDto

class MainPageListAdapter : RecyclerView.Adapter<MainPageListAdapter.MainPageListAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPageListAdapterViewHolder {
        val layout = when(viewType){
            TYPE_POST -> R.layout.item_post
            TYPE_QNA -> R.layout.item_qna
            TYPE_FIND_PARTNER -> R.layout.item_find_partner
            TYPE_ADOPTION -> R.layout.item_adoption
            else -> throw IllegalArgumentException("Invalid type")
        }

        mContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return MainPageListAdapterViewHolder(view)
    }

    companion object {
        private const val TYPE_POST = 0
        private const val TYPE_QNA = 1
        private const val TYPE_FIND_PARTNER = 2
        private const val TYPE_ADOPTION = 3
        lateinit var mContext : Context
    }
    private val differCallback = object : DiffUtil.ItemCallback<ContentTypeDto>(){
        override fun areItemsTheSame(oldItem: ContentTypeDto, newItem: ContentTypeDto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ContentTypeDto, newItem: ContentTypeDto): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: MainPageListAdapterViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class MainPageListAdapterViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView){
        private fun bindPost(item: ContentTypeDto.NormalPostDto){

        }
        private fun bindQNA(item: ContentTypeDto.QNADto){

        }
        private fun bindFindPartner(item: ContentTypeDto.FindPartnerDto){

        }
        private fun bindAdoption(item: ContentTypeDto.AdoptionDto){

        }
        fun bind(model: ContentTypeDto){
            when(model){
                is ContentTypeDto.NormalPostDto -> bindPost(model)
                is ContentTypeDto.QNADto -> bindQNA(model)
                is ContentTypeDto.FindPartnerDto -> bindFindPartner(model)
                is ContentTypeDto.AdoptionDto -> bindAdoption(model)
            }
        }
    }
}