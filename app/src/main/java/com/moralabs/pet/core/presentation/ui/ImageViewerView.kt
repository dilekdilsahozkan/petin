package com.moralabs.pet.core.presentation.ui

import android.animation.Animator
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
import com.moralabs.pet.databinding.UiImageViewerViewBinding


class ImageViewerView(
    context: Context,
    imageUrl: String,
) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen), Animator.AnimatorListener
{
    private var binding = UiImageViewerViewBinding.inflate(layoutInflater)
    private val ANIMATION_DURATION = 100L

    init {
        setContentView(binding.root)

        binding.imageView.loadImageWithPlaceholder(imageUrl)
        binding.close.setOnClickListener { dismiss() }

        binding.dialogBackground.animate().setDuration(ANIMATION_DURATION).alpha(0.6f).start()
        binding.dialogRoot.animate().translationY(0f).setDuration(ANIMATION_DURATION).start()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.imageView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    override fun dismiss() {
        setCancelable(false)
        binding.root.isClickable = false
        binding.dialogBackground.animate().setDuration(ANIMATION_DURATION).alpha(0f).start()
        binding.dialogRoot.animate().setListener(this).alpha(0f).translationY(200f).setDuration(ANIMATION_DURATION).start()
    }

    override fun onAnimationEnd(p0: Animator?) {
        super.dismiss()
    }

    override fun onAnimationCancel(p0: Animator?) {}
    override fun onAnimationRepeat(p0: Animator?) {}
    override fun onAnimationStart(p0: Animator?) {}
}