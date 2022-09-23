package com.moralabs.pet.settings.presentation.ui.about

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentWhatIsPetBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhatIsPetFragment : BaseFragment<FragmentWhatIsPetBinding, UserDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_what_is_pet
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_what_is_pet))
    }

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }
}