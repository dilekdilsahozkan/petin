package com.moralabs.pet.newPost.presentation.ui

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentNewPostBinding
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.newPost.presentation.viewmodel.NewPostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPostFragment :BaseFragment<FragmentNewPostBinding, NewPostDto, NewPostViewModel>() {

    override fun getLayoutId() = R.layout.fragment_new_post

    override fun fragmentViewModel(): BaseViewModel<NewPostDto> {
        val viewModel: NewPostViewModel by viewModels()
        return viewModel
    }
}