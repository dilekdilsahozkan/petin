package com.moralabs.pet.core.presentation

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:visibility")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


@BindingAdapter("pet:src")
fun ImageView.loadImage(src: String?) {
    Glide.with(context).clear(this)

    val image = this
    Glide.with(context).load(src)
        .into(this)
}
