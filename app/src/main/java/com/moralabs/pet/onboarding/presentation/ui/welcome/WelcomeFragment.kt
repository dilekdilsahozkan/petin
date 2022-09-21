package com.moralabs.pet.onboarding.presentation.ui.welcome

import android.content.Intent
import com.moralabs.pet.R
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentWelcomeBinding
import com.moralabs.pet.onboarding.presentation.ui.LoginActivity
import com.moralabs.pet.onboarding.presentation.ui.guest.GuestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding, Any, Nothing>() {

    override fun getLayoutId() = R.layout.fragment_welcome
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun addListeners() {
        super.addListeners()
        binding.loginButton.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
        binding.guestButton.setOnClickListener {
            startActivity(Intent(context, GuestActivity::class.java))
        }
    }

    override fun fragmentViewModel() = BaseViewModel<Any>(BaseUseCase())
}