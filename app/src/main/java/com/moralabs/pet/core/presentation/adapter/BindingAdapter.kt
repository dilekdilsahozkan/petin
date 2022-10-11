package com.moralabs.pet.core.presentation.adapter

import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
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

@BindingAdapter("pet:setTextCustomStyle")
fun TextView.setTextCustomStyle(isNormal:Boolean){
    val regular: Typeface? = ResourcesCompat.getFont(context, R.font.poppins_regular)
    val bold: Typeface? = ResourcesCompat.getFont(context, R.font.poppins_medium)

    if (isNormal) this.typeface = regular else this.typeface = bold
}

