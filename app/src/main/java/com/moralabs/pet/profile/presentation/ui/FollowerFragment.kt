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
import com.moralabs.pet.databinding.FragmentFollowerBinding
import com.moralabs.pet.databinding.ItemFollowTypeBinding
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import com.moralabs.pet.profile.presentation.viewmodel.FollowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment : BaseFragment<FragmentFollowerBinding, List<UserInfoDto>, FollowViewModel>() {

    override fun getLayoutId() = R.layout.fragment_follower
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<UserInfoDto>> {
        val viewModel: FollowViewModel by viewModels()
        return viewModel
    }

    private val followerAdapter: BaseListAdapter<UserInfoDto, ItemFollowTypeBinding> by lazy {
        BaseListAdapter(R.layout.item_follow_type, BR.item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = followerAdapter
        viewModel.getFollowerList()
    }

    override fun addListeners() {
        super.addListeners()
        binding.imgClose.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }
    }

    override fun stateSuccess(data: List<UserInfoDto>) {
        super.stateSuccess(data)

        followerAdapter.submitList(data)
    }
}