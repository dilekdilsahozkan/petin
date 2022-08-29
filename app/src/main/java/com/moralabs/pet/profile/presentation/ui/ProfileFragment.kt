package com.moralabs.pet.profile.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentProfileBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.presentation.adapter.ProfileViewPagerAdapter
import com.moralabs.pet.profile.presentation.viewmodel.ProfileViewModel
import com.moralabs.pet.settings.presentation.ui.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, UserDto, ProfileViewModel>(){

    override fun getLayoutId() = R.layout.fragment_profile

    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: ProfileViewModel by viewModels()
        return viewModel
    }

    override fun addListeners() {
        super.addListeners()
        binding.imgMenu.setOnClickListener {
            startActivity(Intent(context, SettingsActivity::class.java))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when(position) {
                0 -> tab.setIcon(R.drawable.ic_posts)
                1 -> tab.setIcon(R.drawable.ic_pet_house)
            }
        }.attach()

        viewModel.userInfo()
    }

    override fun stateSuccess(data: UserDto) {
        super.stateSuccess(data)

        binding.userFullName.text = data.fullName
        binding.username.text = data.userName
        binding.toolbarUsername.text = data.userName
        binding.totalPost.text = data.postCount.toString()
        if(data.postCount == null){
            binding.totalPost.text = getString(R.string.zero)
        }
        binding.followers.text = data.followerCount.toString()
        binding.following.text = data.followedCount.toString()
        binding.userPhoto.loadImage(data.image)

    }

    private val viewPagerAdapter: ProfileViewPagerAdapter by lazy {
        ProfileViewPagerAdapter(
            this,
            listOf(
                //PostFragment(),
                //PetFragment()
            )
        )
    }
}