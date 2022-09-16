package com.moralabs.pet.onboarding.presentation.ui

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentPasswordCodeBinding
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.presentation.viewmodel.LoginViewModel

class PasswordCodeFragment : BaseFragment<FragmentPasswordCodeBinding, LoginDto, LoginViewModel>() {

    override fun getLayoutId() = R.layout.fragment_password_code
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<LoginDto> {
        val viewModel: LoginViewModel by viewModels()
        return viewModel
    }
}