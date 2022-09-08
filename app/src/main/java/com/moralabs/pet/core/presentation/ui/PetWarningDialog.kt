package com.moralabs.pet.core.presentation.ui

import android.animation.Animator
import android.app.Dialog
import android.content.Context
import com.moralabs.pet.databinding.UiPetWarningDialogBinding

enum class PetWarningDialogResult {
    OK
}

class PetWarningDialog (
    context: Context,
    onResult: ((result: PetWarningDialogResult) -> Unit)? = null,
) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen), Animator.AnimatorListener {

    private var binding = UiPetWarningDialogBinding.inflate(layoutInflater)

    val ANIMATION_DURATION = 100L

    init {
        setContentView(binding.root)

        binding.dialogBackground.animate().setDuration(ANIMATION_DURATION).alpha(0.6f).start()
        binding.dialogRoot.animate().translationY(0f).setDuration(ANIMATION_DURATION).start()

        binding.icon.setOnClickListener{
            onResult?.let {
                onResult(PetWarningDialogResult.OK)
            }
            dismiss()
        }
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