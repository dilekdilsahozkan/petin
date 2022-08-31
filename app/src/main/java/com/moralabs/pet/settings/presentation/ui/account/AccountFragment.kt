package com.moralabs.pet.settings.presentation.ui.account

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentAccountBinding
import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding, SettingsDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_account
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH


    override fun addListeners() {
        super.addListeners()
        binding.icNavigateToPersonalInfo.setOnClickListener{
            findNavController().navigate(R.id.action_accountFragment_to_personalInformationsFragment)
        }
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_my_account))
    }


    override fun fragmentViewModel(): BaseViewModel<SettingsDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

}