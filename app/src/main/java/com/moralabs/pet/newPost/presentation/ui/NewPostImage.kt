package com.moralabs.pet.newPost.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.moralabs.pet.databinding.ItemNewPostImageBinding
import java.io.File

class NewPostImage  : LinearLayout {
    private var file: File? = null
    private lateinit var binding: ItemNewPostImageBinding

    private var listener: NewPostImageListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        binding = ItemNewPostImageBinding.inflate(LayoutInflater.from(context), this, true)
        visibility = View.INVISIBLE

        binding.root.setOnClickListener {
            file?.let { f ->
                listener?.onFileDelete(f)
            }
        }
    }

    fun setFile(file: File?, listener: NewPostImageListener) {
        if (this.file == file) return
        this.file = file
        this.listener = listener

        this.file?.let {
            isVisible = true
            Glide.with(context).clear(this)
            Glide.with(context).load(file).into(binding.photo)
            return@setFile
        }

        visibility = View.INVISIBLE
    }
}