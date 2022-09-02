package com.moralabs.pet.profile.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentPostBinding
import com.moralabs.pet.profile.presentation.viewmodel.ProfilePostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : BaseFragment<FragmentPostBinding, List<PostDto>, ProfilePostViewModel>() {

    override fun getLayoutId() = R.layout.fragment_post

    override fun fragmentViewModel(): BaseViewModel<List<PostDto>> {
        val viewModel: ProfilePostViewModel by viewModels()
        return viewModel
    }

    private val postAdapter: PostListAdapter by lazy {
        PostListAdapter(
            onOfferClick = {

            },
            onLikeClick = {

            },
            onCommentClick = {

            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profilePost()
    }

    override fun stateSuccess(data: List<PostDto>) {
        super.stateSuccess(data)

        postAdapter.submitList(data)
    }
}