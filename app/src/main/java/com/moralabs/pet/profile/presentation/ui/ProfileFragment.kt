package com.moralabs.pet.profile.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentProfileBinding
import com.moralabs.pet.profile.data.remote.dto.ProfileDto
import com.moralabs.pet.profile.presentation.adapter.ProfileViewPagerAdapter
import com.moralabs.pet.profile.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, UserDto, ProfileViewModel>(){

    override fun getLayoutId() = R.layout.fragment_profile

    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: ProfileViewModel by viewModels()
        return viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.profileViewpager
        val tabLayout = binding.tabLayout
        val adapter = ProfileViewPagerAdapter(requireFragmentManager(), lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.setIcon(R.drawable.ic_posts)
                1 -> tab.setIcon(R.drawable.ic_pet_house)
            }
        }.attach()
    }
}