package com.moralabs.pet.message.presentation.toolbar

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.appbar.MaterialToolbar
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.databinding.UiToolbarMessageDetailBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto

class MessageDetailAppBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    MaterialToolbar(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, -1)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    private var binding =
        UiToolbarMessageDetailBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.imgBack.setOnClickListener {
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

    fun setUser(userDto: UserDto?) {
        binding.item = userDto
    }
}