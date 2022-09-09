package com.moralabs.pet.mainPage.presentation.ui

import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityCommentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentActivity : BaseActivity<ActivityCommentBinding>(),
    PetToolbarListener {

    companion object {
        const val POST_ID = "postId"
    }

    override fun getLayoutId() = R.layout.activity_comment

}