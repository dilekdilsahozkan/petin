package com.moralabs.pet.core.presentation.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto

@BindingAdapter("pet:src")
fun ImageView.loadImage(src: String?) {
    Glide.with(context).clear(this)

    val image = this
    Glide.with(context).load(src)
        .into(this)
}

@BindingAdapter("pet:attributes")
fun TextView.attributes(list: List<PetAttributeDto>?){
    text = list?.filter { it.type == 8 }?.get(0)?.choice
}

