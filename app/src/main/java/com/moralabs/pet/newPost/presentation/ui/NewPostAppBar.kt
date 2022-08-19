package com.moralabs.pet.newPost.presentation.ui

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.appbar.MaterialToolbar
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.databinding.UiToolbarNewPostBinding

class NewPostAppBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    MaterialToolbar(context, attrs, defStyleAttr){
    constructor(context: Context) : this(context, null, -1)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    private var binding: UiToolbarNewPostBinding= UiToolbarNewPostBinding.inflate(LayoutInflater.from(context),this,true)

    init {
        binding.imgClose.setOnClickListener {
            _listener?.onItemSelected(it.id)
        }
        binding.imgSelect.setOnClickListener {
            _listener?.onItemSelected(it.id)
        }
        setBackgroundColor(Color.WHITE)
    }

    private val _listener: PetToolbarListener? by lazy {
        (context as? ContextWrapper)?.baseContext as? PetToolbarListener
    }

    fun showTitleText(title: String?) {
        binding.toolbarTitle.text = title
    }

    fun showLightColorBar() {
        binding.root.setBackgroundColor(context.getColor(R.color.white))
        binding.toolbarTitle.setTextColor(context.getColor(R.color.darkPrimary))
    }
}