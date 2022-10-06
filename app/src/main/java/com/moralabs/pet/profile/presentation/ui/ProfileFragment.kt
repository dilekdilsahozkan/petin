package com.moralabs.pet.profile.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
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
class ProfileFragment : BaseFragment<FragmentProfileBinding, UserDto, ProfileViewModel>(), BlockUnblockBottomSheetListener,
    FollowUnfollowBottomSheetListener {

    override fun getLayoutId() = R.layout.fragment_profile
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    private val otherUserId: String? by lazy {
        activity?.intent?.getStringExtra(ProfileActivity.OTHER_USER_ID)
    }
    private var userInfo: UserDto? = null
    private var isUserBlocked: Boolean = false


    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: ProfileViewModel by viewModels()
        return viewModel
    }

    override fun addListeners() {
        super.addListeners()
        binding.imgMenu.setOnClickListener {
            if (otherUserId.isNullOrBlank()) {
                startActivity(Intent(context, SettingsActivity::class.java))
            } else {
                userInfo?.let { user ->
                    OtherUserActionBottomSheetFragment(
                        user.isUserFollowed,
                        isUserBlocked,
                        this@ProfileFragment,
                        this@ProfileFragment
                    ).show(this.parentFragmentManager, "OtherUserActionBottomSheetFragment")
                }
            }
        }

        binding.followedLinear.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_profile_to_followedFragment)
        }

        binding.followerLinear.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_profile_to_followersFragment)
        }

        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_pet_house)
                1 -> tab.setIcon(R.drawable.ic_posts)
            }
        }.attach()

        getUserInfo()
    }

    override fun stateSuccess(data: UserDto) {
        super.stateSuccess(data)
        userInfo = data
        binding.userFullName.text = data.fullName.toString()
        binding.username.text = data.userName.toString()
        binding.toolbarUsername.text = data.userName.toString()
        if (data.isUserBlockedByUser != true) {
            setProfileUI(data)
        } else {
            otherUserBlockedUI()
        }
        if (isUserBlocked) {
            blockedUserUI()
        }
    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.followUserState.collect {
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
            viewModel.unfollowUserState.collect {
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
            viewModel.blockedListState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<List<UserInfoDto>> -> {
                        isUserBlocked = false
                        it.data.forEach {
                            if (it.userId == otherUserId) {
                                isUserBlocked = true
                            }
                        }
                        viewModel.otherUsersInfo(otherUserId)
                    }
                    is ViewState.Error<*> -> {
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.blockUserState.collect {
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
            viewModel.unblockUserState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<Boolean> -> {
                        if (it.data && isUserBlocked) {
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
                PetFragment(),
                PostFragment(),
            )
        )
    }

    private fun getUserInfo() {
        if (!otherUserId.isNullOrBlank()) {
            viewModel.getBlockedList()
        } else {
            viewModel.userInfo()
            binding.imgBack.visibility = View.GONE
        }
    }

    override fun onBlockUnblockItemClick(isUserBlocked: Boolean?) {
        isUserBlocked?.let {
            if (it) {
                viewModel.unblockUser(otherUserId)
            } else {
                viewModel.blockUser(otherUserId)
            }
        }
    }

    override fun onFollowUnfollowItemClick(isUserFollowed: Boolean?) {
        isUserFollowed?.let {
            if (it) {
                viewModel.unfollowUser(otherUserId)
            } else {
                viewModel.followUser(otherUserId)
            }
        }
    }

    private fun setProfileUI(data: UserDto) {
        binding.userSocialInfo.visibility = View.VISIBLE
        if(otherUserId.isNullOrBlank().not()) {
            binding.viewpager.adapter = viewPagerAdapter
        }
        binding.totalPost.text = data.postCount.toString()
        if (data.postCount == null) {
            binding.totalPost.text = getString(R.string.zero)
        }
        binding.followers.text = data.followerCount.toString()
        binding.following.text = data.followedCount.toString()
        binding.userPhoto.loadImageWithPlaceholder(data.media?.url)
    }

    private fun otherUserBlockedUI() {
        binding.userSocialInfo.visibility = View.GONE
        binding.imgMenu.visibility = View.GONE
        binding.blockMessage.visibility = View.VISIBLE
        Glide.with(this)
            .load(R.drawable.ic_error_profile)
            .into(binding.userPhoto)
    }

    private fun blockedUserUI() {
        binding.userSocialInfo.visibility = View.GONE
        binding.viewpager.adapter = null
        Glide.with(this)
            .load(R.drawable.ic_error_profile)
            .into(binding.userPhoto)
    }
}