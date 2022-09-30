package com.moralabs.pet.onboarding.presentation.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentForgotPasswordBinding
import com.moralabs.pet.onboarding.data.remote.dto.ForgotPasswordDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding, LoginDto, LoginViewModel>() {

    override fun getLayoutId() = R.layout.fragment_forgot_password
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<LoginDto> {
        val viewModel: LoginViewModel by viewModels()
        return viewModel
    }

    private val passwordActivity by lazy {
        activity as? LoginActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.forgotState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<*> -> {
                        passwordActivity?.getPassword()?.email = binding.emailEdittext.text.toString()
                        findNavController().navigate(
                            R.id.action_fragment_forgot_to_passwordCodeFragment
                        )
                    }
                    is ViewState.Error<*> -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_wrong_email),
                            Toast.LENGTH_LONG
                        ).show()
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun addListeners() {
        super.addListeners()
        binding.nextButton.setOnClickListener {
            viewModel.forgotPassword(
                ForgotPasswordDto(
                    email = binding.emailEdittext.text.toString(),
                    codeType = ForgotPw.FORGOT_PASSWORD.value
                )
            )
        }
    }

}

enum class ForgotPw(val value :Int){
    FORGOT_PASSWORD(1)
}