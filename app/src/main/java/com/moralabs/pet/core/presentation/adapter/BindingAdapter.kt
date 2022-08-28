package com.moralabs.pet.core.presentation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.moralabs.pet.databinding.ItemNotificationBinding
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import com.moralabs.pet.notification.presentation.ui.NotificationActivity

@BindingAdapter("android:visibility")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("pet:notification")
fun LinearLayout.loadFields(notifications: List<NotificationDto>?) {

    removeAllViews()

    notifications?.forEachIndexed { _, notification ->
        val item = ItemNotificationBinding.inflate(LayoutInflater.from(context))
        item.item = notification

        item.root.setOnClickListener {
            var bundle = bundleOf(NotificationActivity.NOTIFICATION_TEXT to notification.text, NotificationActivity.NOTIFICATION_TYPE to notification.type, NotificationActivity.NOTIFICATION_DATETIME to notification.dateTime)
            val intent = Intent(context, NotificationActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
        addView(item.root)
    }

    (parent as? ViewGroup)?.isVisible = true
}

@BindingAdapter("pet:src")
fun ImageView.loadImage(src: String?) {
    Glide.with(context).clear(this)

    val image = this
    Glide.with(context).load(src)
        .into(this)
}

