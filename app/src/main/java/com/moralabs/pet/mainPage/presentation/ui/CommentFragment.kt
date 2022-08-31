package com.moralabs.pet.mainPage.presentation.ui

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentCommentBinding
import com.moralabs.pet.mainPage.presentation.viewmodel.CommentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : BaseFragment<FragmentCommentBinding, List<PostDto>, CommentViewModel>() {

    override fun getLayoutId() = R.layout.fragment_comment

    override fun fragmentViewModel(): BaseViewModel<List<PostDto>> {
        val viewModel: CommentViewModel by viewModels()
        return viewModel
    }
}