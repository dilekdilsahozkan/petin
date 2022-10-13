package com.moralabs.pet.settings.presentation.ui.about

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.extension.fromHtml
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.FragmentWhatIsPetBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WhatIsPetFragment : BaseFragment<FragmentWhatIsPetBinding, UserDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_what_is_pet
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_what_is_pet))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getInfo(InfoTypes.WHAT_IS_PET.type)
    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.stateInfo.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<String> -> {
                        viewModel.description = it.data
                        binding.whatIsPetText.text = viewModel.description.fromHtml()
                        stopLoading()
                    }
                    is ViewState.Error<*> -> {
                        stopLoading()
                    }
                }
            }
        }
    }
}