package com.moralabs.pet.core.presentation.ui

import android.animation.Animator
import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import com.moralabs.pet.R
import com.moralabs.pet.databinding.UiPetWarningDialogBinding
import kotlinx.android.synthetic.main.ui_pet_warning_dialog.*

enum class PetWarningDialogType {
    WARNING,
    CONFIRMATION,
    LOGIN
}

enum class PetWarningDialogResult {
    OK,
    CANCEL
}

class PetWarningDialog (
    context: Context,
    type: PetWarningDialogType,
    title: String,
    okay: String,
    description: String,
    onResult: ((result: PetWarningDialogResult) -> Unit)? = null,
    positiveButton: String? = null,
    negativeButton: String? = null
) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar), Animator.AnimatorListener {

    private var binding = UiPetWarningDialogBinding.inflate(layoutInflater)

    val ANIMATION_DURATION = 100L

    init {
        setContentView(binding.root)

        binding.dialogBackground.animate().setDuration(ANIMATION_DURATION).alpha(0.6f).start()
        binding.dialogRoot.animate().translationY(0f).setDuration(ANIMATION_DURATION).start()

        when(type){
            PetWarningDialogType.WARNING -> {
                setCancelable(true)
                binding.icon.setImageResource(R.drawable.ic_warning)
            }
            PetWarningDialogType.CONFIRMATION -> {
                setCancelable(false)
                binding.icon.setImageResource(R.drawable.ic_error_profile)
            }
            PetWarningDialogType.LOGIN -> {
                setCancelable(false)
                binding.icon.setImageResource(R.drawable.ic_warning)
            }
        }

        binding.text.text = title
        binding.description.text = description
        binding.okay.text = okay

        if(title.equals(context.getString(R.string.register))){
            binding.icon.visibility = View.GONE
        } else {
            binding.icon.visibility = View.VISIBLE
        }

        binding.okay.setOnClickListener{
            onResult?.let {
                onResult(PetWarningDialogResult.OK)
            }
            dismiss()
        }

        binding.discard.setOnClickListener{
            onResult?.let {
                onResult(PetWarningDialogResult.CANCEL)
            }
            dismiss()
        }

        binding.discard.isVisible = type == PetWarningDialogType.CONFIRMATION
        binding.discard.isVisible = type == PetWarningDialogType.LOGIN

        positiveButton?.let{
            binding.okay.text = it
        }

        negativeButton?.let{
            binding.discard.text = it
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