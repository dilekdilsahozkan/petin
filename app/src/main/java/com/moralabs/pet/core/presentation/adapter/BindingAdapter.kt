package com.moralabs.pet.core.presentation.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("pet:src")
fun ImageView.loadImage(src: String?) {
    Glide.with(context).clear(this)

    val image = this
    Glide.with(context).load(src)
        .into(this)
}

