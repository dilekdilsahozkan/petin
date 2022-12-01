package com.moralabs.pet.settings.presentation.ui

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.facebook.login.LoginManager
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.databinding.FragmentSettingsBinding
import com.moralabs.pet.onboarding.presentation.ui.welcome.WelcomeActivity
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, UserDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_settings
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

    override fun addListeners() {
        super.addListeners()

        binding.tvLogOut.setOnClickListener {
            PetWarningDialog(
                requireContext(),
                PetWarningDialogType.CONFIRMATION,
                resources.getString(R.string.ask_sure),
                okay = getString(R.string.yes),
                discard = getString(R.string.no),
                description = resources.getString(R.string.logoutSure),
                negativeButton = resources.getString(R.string.no),
                onResult = {
                    if (PetWarningDialogResult.OK == it) {
                        //TODO:bunu kontrol et....
                        LoginManager.getInstance().logOut()
                        viewModel.logout()
                        val intent = Intent(context, WelcomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
            ).show()
        }

        binding.account.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_settings_to_accountFragment)
        }
        binding.offers.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_offersFragment)
        }
        binding.privacyAndSec.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_privacyAndSecurityFragment)
        }
        binding.about.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }
    }
}