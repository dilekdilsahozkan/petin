package com.moralabs.pet.settings.presentation.ui.privacyAndSecurity

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentPrivacyAndSecurityBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyAndSecurityFragment : BaseFragment<FragmentPrivacyAndSecurityBinding, UserDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_privacy_and_security
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH


    override fun addListeners() {

        binding.tvBlockedAccounts.setOnClickListener {
            findNavController().navigate(R.id.action_privacyAndSecurityFragment_to_blockedAccountsFragment)
        }
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_privacy_and_security))
    }

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

}