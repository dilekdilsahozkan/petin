package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.appBar)
    }

    override fun showTitleText(title: String?) {
        binding.appBar.showTitleText(title)
    }

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.img_back -> super.onBackPressed()
        }
    }
}