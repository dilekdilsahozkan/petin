package com.moralabs.pet.settings.presentation.ui

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentSettingsBinding
import com.moralabs.pet.onboarding.presentation.ui.WelcomeActivity
import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_settings
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun addListeners() {
        super.addListeners()

        binding.tvLogOut.setOnClickListener {
            viewModel.logout()
            val intent = Intent(context, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.icNavigateToAccount.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_settings_to_accountFragment)
        }
        binding.icNavigateToPrivacyAndSec.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_privacyAndSecurityFragment)
        }
    }

    override fun fragmentViewModel(): BaseViewModel<SettingsDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }
}