package com.moralabs.pet.message.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseViewPagerAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.BaseViewPagerFragment
import com.moralabs.pet.databinding.FragmentProfileBinding
import com.moralabs.pet.databinding.ItemProfilePostBinding
import com.moralabs.pet.profile.data.remote.dto.ProfileDto
import com.moralabs.pet.profile.data.remote.dto.ProfilePostsDto
import com.moralabs.pet.profile.presentation.viewmodel.ProfileViewModel
import com.softtech.imecemobil.presentation.common.adapter.BaseListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileDto, ProfileViewModel>(){

    private val profilePostsAdapter: BaseListAdapter<ProfilePostsDto, ItemProfilePostBinding> by lazy {
        BaseListAdapter(R.layout.item_profile_post, BR.item, onRowClick = {

        }, isSameEntity = { oldItem, newItem ->
            oldItem.type == newItem.type
        })
    }

    private val viewPagerAdapter: BaseViewPagerAdapter by lazy {
        BaseViewPagerAdapter(
            this,
            listOf(
                BaseViewPagerFragment(profilePostsAdapter)
            )
        )
    }

    override fun getLayoutId() = R.layout.fragment_profile

    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<ProfileDto> {
        val viewModel: ProfileViewModel by viewModels()
        return viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileViewpager.adapter = viewPagerAdapter
    }

    override fun stateSuccess(data: ProfileDto) {
        super.stateSuccess(data)

        binding.item = data

        profilePostsAdapter.submitList(
            listOf(
                ProfilePostsDto(
                    type = 1,
                    username = "gokalp.okumus",
                    location = "Ankara",
                    postText = "Selammm"
                )
            )
        )
    }
}