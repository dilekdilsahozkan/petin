package com.moralabs.pet.profile.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentProfilePostsBinding
import com.moralabs.pet.profile.presentation.viewmodel.ProfilePostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilePostFragment : BaseFragment<FragmentProfilePostsBinding, List<PostDto>, ProfilePostViewModel>() {

    override fun getLayoutId() = R.layout.fragment_profile_posts

    override fun fragmentViewModel(): BaseViewModel<List<PostDto>> {
        val viewModel: ProfilePostViewModel by viewModels()
        return viewModel
    }

    private val profilePostAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PostListAdapter(
            onRowClick = { post ->
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = profilePostAdapter
            setHasFixedSize(true)
        }
        viewModel.profilePost()
    }

    override fun stateSuccess(data: List<PostDto>) {
        super.stateSuccess(data)

        profilePostAdapter.submitList(data)
    }
}
