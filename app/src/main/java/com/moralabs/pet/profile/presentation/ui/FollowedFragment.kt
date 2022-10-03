package com.moralabs.pet.profile.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentFollowedBinding
import com.moralabs.pet.databinding.ItemFollowTypeBinding
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import com.moralabs.pet.profile.presentation.viewmodel.FollowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowedFragment : BaseFragment<FragmentFollowedBinding, List<UserInfoDto>, FollowViewModel>() {

    override fun getLayoutId() = R.layout.fragment_followed
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<UserInfoDto>> {
        val viewModel: FollowViewModel by viewModels()
        return viewModel
    }

    private val followedAdapter: BaseListAdapter<UserInfoDto, ItemFollowTypeBinding> by lazy {
        BaseListAdapter(R.layout.item_follow_type, BR.item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = followedAdapter
        viewModel.getFollowedList()
    }

    override fun addListeners() {
        super.addListeners()
        binding.imgClose.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }
    }

    override fun stateSuccess(data: List<UserInfoDto>) {
        super.stateSuccess(data)

        followedAdapter.submitList(data)
    }
}