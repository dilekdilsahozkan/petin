package com.moralabs.pet.profile.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentProfileBinding
import com.moralabs.pet.petProfile.presentation.ui.PetFragment
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import com.moralabs.pet.profile.presentation.adapter.ProfileViewPagerAdapter
import com.moralabs.pet.profile.presentation.viewmodel.ProfileViewModel
import com.moralabs.pet.settings.presentation.ui.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, UserDto, ProfileViewModel>() {

    override fun getLayoutId() = R.layout.fragment_profile
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    private val otherUserId: String? by lazy {
        activity?.intent?.getStringExtra(ProfileActivity.OTHER_USER_ID)
    }

    private var isUserFollowed: Boolean = false

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: ProfileViewModel by viewModels()
        return viewModel
    }

    override fun addListeners() {
        super.addListeners()
        binding.imgMenu.setOnClickListener {
            startActivity(Intent(context, SettingsActivity::class.java))
        }

        binding.followUnfollowUser.setOnClickListener {
            when (isUserFollowed) {
                true -> {
                    viewModel.unfollowUser(otherUserId)
                }
                else -> {
                    viewModel.followUser(otherUserId)
                }
            }
        }

        binding.followedLinear.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_profile_to_followedFragment)
        }

        binding.followerLinear.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_profile_to_followersFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_posts)
                1 -> tab.setIcon(R.drawable.ic_pet_house)
            }
        }.attach()

        getUserInfo()
    }

    override fun stateSuccess(data: UserDto) {
        super.stateSuccess(data)

        binding.userFullName.text = data.fullName.toString()
        binding.username.text = data.userName.toString()
        binding.toolbarUsername.text = data.userName.toString()
        binding.totalPost.text = data.postCount.toString()
        if (data.postCount == null) {
            binding.totalPost.text = getString(R.string.zero)
        }
        binding.followers.text = data.followerCount.toString()
        binding.following.text = data.followedCount.toString()
        binding.userPhoto.loadImage(data.media?.url)

    }

    override fun addObservers() {
        super.addObservers()
        lifecycleScope.launch {
            viewModel.followedListState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<List<UserInfoDto>> -> {
                        isUserFollowed = false
                        it.data.forEach { user ->
                            if (user.userId == otherUserId)
                                isUserFollowed = true
                        }
                        binding.followUnfollowUser.visibility = View.VISIBLE
                        if (isUserFollowed == true) {
                            binding.followUnfollowUser.text =
                                context?.getString(R.string.unfollow_user)
                        } else {
                            binding.followUnfollowUser.text =
                                context?.getString(R.string.follow_user)
                        }
                        stopLoading()
                    }
                    is ViewState.Error<*> -> {
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.followState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<Boolean> -> {
                        if (it.data) {
                            getUserInfo()
                        }
                    }
                    is ViewState.Error<*> -> {
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.unfollowState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<Boolean> -> {
                        if (it.data) {
                            getUserInfo()
                        }
                    }
                    is ViewState.Error<*> -> {
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    private val viewPagerAdapter: ProfileViewPagerAdapter by lazy {
        ProfileViewPagerAdapter(
            this,
            listOf(
                PostFragment(),
                PetFragment()
            )
        )
    }

    private fun getUserInfo() {
        if (!otherUserId.isNullOrBlank()) {
            viewModel.otherUsersInfo(otherUserId)
            viewModel.getFollowedList()
            binding.imgMenu.visibility = View.GONE
        } else {
            viewModel.userInfo()
        }
    }
}