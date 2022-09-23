package com.moralabs.pet.onboarding.presentation.ui.welcome

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentWelcomeBinding
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.presentation.ui.login.LoginActivity
import com.moralabs.pet.onboarding.presentation.ui.guest.GuestActivity
import com.moralabs.pet.onboarding.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : View.OnClickListener,
    BaseFragment<FragmentWelcomeBinding, LoginDto, LoginViewModel>() {

    override fun getLayoutId() = R.layout.fragment_welcome
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<LoginDto> {
        val viewModel: LoginViewModel by viewModels()
        return viewModel
    }

    override fun addListeners() {
        super.addListeners()

        binding.loginButton.setOnClickListener(this)
        binding.guestButton.setOnClickListener(this)

    }

    override fun stateSuccess(data: LoginDto) {
        super.stateSuccess(data)
        startActivity(Intent(context, GuestActivity::class.java))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_button -> {
                startActivity(Intent(context, LoginActivity::class.java))
            }
            R.id.guest_button -> {
                viewModel.guestLogin()
            }
        }
    }
}