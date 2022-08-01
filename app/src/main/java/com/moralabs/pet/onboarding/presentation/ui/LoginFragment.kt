package com.moralabs.pet.onboarding.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseFragment
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.databinding.FragmentLoginBinding
import com.moralabs.pet.mainPage.presentation.MainPageActivity
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginRequestDto
import com.moralabs.pet.onboarding.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginDto, LoginViewModel>() {

    override fun getLayoutId() = R.layout.fragment_login
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<LoginDto> {
        val viewModel: LoginViewModel by viewModels()
        return viewModel
    }

    override fun addListeners() {
        super.addListeners()
            binding.loginButton.setOnClickListener {
                if(binding.emailEdittext.text.toString().isNotEmpty() && binding.passwordEdittext.text.toString().isNotEmpty()){
                    startActivity(Intent(context, MainPageActivity::class.java))
                    viewModel.login(LoginRequestDto(
                        this.email_edittext.toString(),
                        this.password_edittext.toString()))
                }
            }
    }
}