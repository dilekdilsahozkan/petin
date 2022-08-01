package com.moralabs.pet.onboarding.presentation.ui

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseFragment
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.databinding.FragmentRegisterBinding
import com.moralabs.pet.onboarding.data.remote.dto.RegisterDto
import com.moralabs.pet.onboarding.presentation.viewmodel.RegisterViewModel
import com.moralabs.pet.onboarding.presentation.viewmodel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding, RegisterDto, RegisterViewModel>()  {

    override fun getLayoutId() = R.layout.fragment_register
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<RegisterDto> {
        val viewModel: RegisterViewModel by viewModels()
        return viewModel
    }

}