package com.moralabs.pet.onboarding.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.databinding.FragmentWelcomeBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.onboarding.data.remote.dto.WelcomeDto
import com.moralabs.pet.onboarding.presentation.viewmodel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding, WelcomeDto, WelcomeViewModel>() {

    override fun getLayoutId() = R.layout.fragment_welcome

    override fun fragmentViewModel(): BaseViewModel<WelcomeDto> {
        val viewModel: WelcomeViewModel by viewModels()
        return viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.welcomePet()
    }

    override fun addListeners() {
        super.addListeners()
        binding.loginButton.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
        binding.guestButton.setOnClickListener {
            startActivity(Intent(context, MainPageActivity::class.java))
        }
    }
}