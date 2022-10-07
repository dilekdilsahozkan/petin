package com.moralabs.pet.core.presentation.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.moralabs.pet.R
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto

@BindingAdapter("pet:src")
fun ImageView.loadImage(src: String?) {
    Glide.with(context).clear(this)

    Glide.with(context).load(src)
        .centerCrop()
        .into(this)
}

@BindingAdapter("pet:srcWithPlaceholder")
fun ImageView.loadImageWithPlaceholder(src: String?) {
    Glide.with(context).clear(this)

    Glide.with(context).load(src)
        .placeholder(R.drawable.ic_add_photo)
        .into(this)
}

@BindingAdapter("pet:attributes")
fun TextView.attributes(list: List<PetAttributeDto>?){
    text = list?.filter { it.attributeType.toString() == tag}?.getOrNull(0)?.choice
}

