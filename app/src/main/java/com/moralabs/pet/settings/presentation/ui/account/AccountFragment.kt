package com.moralabs.pet.settings.presentation.ui.account

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentAccountBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding, UserDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_account
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_my_account))
    }

    override fun addListeners() {
        super.addListeners()
        binding.personalInfo.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_personalInformationsFragment)
        }
        binding.favorites.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_favoritesFragment)
        }
        binding.changePW.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
        }
        binding.deleteAccount.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_deleteAccountFragment)
        }
    }
}