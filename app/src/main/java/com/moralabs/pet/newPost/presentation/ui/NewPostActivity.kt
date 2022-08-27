package com.moralabs.pet.newPost.presentation.ui

import android.os.Bundle
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityNewPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPostActivity : BaseActivity<ActivityNewPostBinding>(),
    PetToolbarListener {

    companion object {
        var BUNDLE_CHOOSE_TYPE = "type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.appBar.rightIcon(R.layout.activity_new_post)

    }

    override fun getLayoutId() = R.layout.activity_new_post

    override fun showTitleText(title: String?) {
        binding.appBar.showTitleText(title)
    }
}