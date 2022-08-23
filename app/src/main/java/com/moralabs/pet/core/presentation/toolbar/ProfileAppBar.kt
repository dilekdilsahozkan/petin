package com.moralabs.pet.core.presentation.toolbar

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.appbar.MaterialToolbar
import com.moralabs.pet.R
import com.moralabs.pet.databinding.UiToolbarProfileBinding

class ProfileAppBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    MaterialToolbar(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, -1)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    private var binding: UiToolbarProfileBinding =
        UiToolbarProfileBinding.inflate(LayoutInflater.from(context), this, true)

    fun showTitleText(title: String?) {
        binding.profileToolbarTitle.text = title
    }
}