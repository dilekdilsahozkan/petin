package com.moralabs.pet.profile.presentation.ui

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.appbar.MaterialToolbar
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.databinding.UiToolbarProfileBinding

class ProfileAppBar (context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    MaterialToolbar(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, -1)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    private var binding: UiToolbarProfileBinding = UiToolbarProfileBinding.inflate(
        LayoutInflater.from(context), this, true)

    init {
        binding.imgMenu.setOnClickListener {
            _listener?.onItemSelected(it.id)
        }
    }

    private val _listener: PetToolbarListener? by lazy {
        (context as? ContextWrapper)?.baseContext as? PetToolbarListener
    }

    fun showTitleText(title: String?) {
        binding.toolbarTitle.text = title
    }

    fun visibilityChange() {
        binding.profileToolBar.visibility = View.GONE
    }

    fun showLightColorBar() {
        binding.root.setBackgroundColor(context.getColor(R.color.mainColor))
        binding.toolbarTitle.setTextColor(context.getColor(R.color.darkPrimary))
    }
}