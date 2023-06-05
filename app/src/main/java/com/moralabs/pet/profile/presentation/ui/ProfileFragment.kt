package com.moralabs.pet.profile.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.databinding.FragmentProfileBinding
import com.moralabs.pet.petProfile.presentation.ui.PetFragment
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.presentation.adapter.ProfileViewPagerAdapter
import com.moralabs.pet.profile.presentation.viewmodel.ProfileViewModel
import com.moralabs.pet.settings.presentation.ui.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, UserDto, ProfileViewModel>(), BlockUnblockBottomSheetListener,
    ReportUserBottomSheetListener, FollowUnfollowBottomSheetListener, UserReportBottomSheetListener {

    private val otherUserId: String? by lazy {
        activity?.intent?.getStringExtra(ProfileActivity.OTHER_USER_ID)
    }
    private val visibility: Boolean? by lazy {
        activity?.intent?.getBooleanExtra(ProfileActivity.OTHER_USER_ID, true)
    }
    private var userInfo: UserDto? = null

    override fun getLayoutId() = R.layout.fragment_profile
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: ProfileViewModel by viewModels()
        return viewModel
    }

    private val viewPagerAdapter: ProfileViewPagerAdapter by lazy {
        ProfileViewPagerAdapter(
            this,
            listOf(
                PostFragment(),
                PetFragment(),
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserInfo()
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
                        user.isBlocked,
                        otherUserId,
                        this@ProfileFragment,
                        this@ProfileFragment,
                        this@ProfileFragment,
                    ).show(this.parentFragmentManager, "OtherUserActionBottomSheetFragment")
                }
            }
        }

        binding.followedLinear.setOnClickListener {
            if (otherUserId.isNullOrBlank()) {
                binding.viewpager.adapter = null
                findNavController().navigate(R.id.action_fragment_profile_to_followedFragment)
            }
        }

        binding.followerLinear.setOnClickListener {
            if (otherUserId.isNullOrBlank()) {
                binding.viewpager.adapter = null
                findNavController().navigate(R.id.action_fragment_profile_to_followersFragment)
            }
        }

        binding.editUserProfile.setOnClickListener {
            if (otherUserId.isNullOrBlank()) {
                binding.viewpager.adapter = null
                findNavController().navigate(R.id.action_fragment_profile_to_personalInfoFragment)
            }
        }

        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
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
                        stateError(it.message)
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
                        stateError(it.message)
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
                        stateError(it.message)
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
                        if (it.data) {
                            getUserInfo()
                        }
                    }
                    is ViewState.Error<*> -> {
                        stateError(it.message)
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.reportState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<Boolean> -> {
                        if (it.data) {
                            getUserInfo()
                        }
                        Toast.makeText(requireContext(), getString(R.string.success_report), Toast.LENGTH_SHORT).show()
                    }
                    is ViewState.Error<*> -> {
                        stateError(it.message)
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (binding.viewpager.adapter == null && userInfo?.isBlocked != true) {
            binding.viewpager.adapter = viewPagerAdapter

            TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
                when (position) {
                    0 -> tab.setIcon(R.drawable.ic_posts)
                    1 -> tab.setIcon(R.drawable.ic_pet_house)
                }
            }.attach()
        }
    }

    override fun stateSuccess(data: UserDto) {
        super.stateSuccess(data)
        userInfo = data
        binding.userFullName.text = data.fullName.toString()
        binding.username.text = data.userName.toString()
        binding.toolbarUsername.text = data.userName.toString()
        if (data.isUserBlockedByUser == true) {
            otherUserBlockedUI()
        } else if (data.isBlocked == true) {
            blockedUserUI()
        } else {
            setProfileUI(data)
        }
    }

    private fun getUserInfo() {
        if (otherUserId.isNullOrBlank().not()) {
            viewModel.otherUsersInfo(otherUserId)
            binding.editUserProfile.visibility = View.GONE
        } else {
            viewModel.userInfo()
            binding.imgBack.visibility = View.GONE
            binding.editUserProfile.visibility = View.VISIBLE
        }
    }

    override fun onBlockUnblockItemClick(isUserBlocked: Boolean?) {
        isUserBlocked?.let {
            if (it) {
                PetWarningDialog(
                    requireContext(),
                    PetWarningDialogType.CONFIRMATION,
                    resources.getString(R.string.ask_sure),
                    okay = getString(R.string.yes),
                    discard = getString(R.string.no),
                    description = getString(R.string.user_unblock),
                    negativeButton = resources.getString(R.string.no),
                    onResult = {
                        if (PetWarningDialogResult.OK == it) {
                            activity?.setResult(Activity.RESULT_OK)
                            viewModel.unblockUser(otherUserId)
                        }
                    }
                ).show()
            } else {
                PetWarningDialog(
                    requireContext(),
                    PetWarningDialogType.CONFIRMATION,
                    resources.getString(R.string.ask_sure),
                    okay = getString(R.string.yes),
                    discard = getString(R.string.no),
                    description = getString(R.string.user_block),
                    negativeButton = resources.getString(R.string.no),
                    onResult = {
                        if (PetWarningDialogResult.OK == it) {
                            activity?.setResult(Activity.RESULT_OK)
                            viewModel.blockUser(otherUserId)
                        }
                    }
                ).show()
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
        if (otherUserId.isNullOrBlank().not() && binding.viewpager.adapter == null) {
            binding.viewpager.adapter = viewPagerAdapter
            viewPagerAdapter.notifyDataSetChanged()
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

    override fun onReportItemClick(userId: String?, isUserReported: Int?) {
        if(isUserReported == 0)
            UserReportBottomSheetFragment(this, otherUserId).show(childFragmentManager, null)
    }

    override fun onSelectReportClick(userId: String?, reportType: Int?) {
        PetWarningDialog(
            requireContext(),
            PetWarningDialogType.CONFIRMATION,
            resources.getString(R.string.ask_sure),
            okay = getString(R.string.yes),
            discard = getString(R.string.no),
            description = resources.getString(R.string.submit_user_report_warning),
            negativeButton = resources.getString(R.string.no),
            onResult = {
                if (PetWarningDialogResult.OK == it) {
                    viewModel.reportUser(userId, reportType)
                }
            }
        ).show()
    }
}