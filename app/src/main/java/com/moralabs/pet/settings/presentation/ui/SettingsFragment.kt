package com.moralabs.pet.settings.presentation.ui

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.facebook.login.LoginManager
import com.google.gson.Gson
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.AuthenticationDto
import com.moralabs.pet.core.domain.AuthenticationUseCase
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.FragmentSettingsBinding
import com.moralabs.pet.onboarding.presentation.ui.welcome.WelcomeActivity
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.data.remote.dto.SettingsRequestDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, UserDto, SettingsViewModel>() {

    companion object {
        private const val USER_KEY = "user"
    }

    override fun getLayoutId() = R.layout.fragment_settings
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

    @Inject
    override lateinit var authenticationUseCase: AuthenticationUseCase

    private val preferences by lazy {
        context?.let {
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

            EncryptedSharedPreferences.create(
                "encrypted_preferences",
                masterKeyAlias,
                it,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    override fun addListeners() {
        super.addListeners()

        val refreshToken = preferences?.getString(USER_KEY, "")
        val json = Gson().fromJson(refreshToken, AuthenticationDto::class.java)

        lifecycleScope.launch {
            viewModel.logoutState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<*> -> {
                        val intent = Intent(context, WelcomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        authenticationUseCase.logout()
                    }
                    is ViewState.Error<*> -> {
                        stateError(it.message)
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }

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
                        viewModel.logout(SettingsRequestDto( json.refreshKey ?: "" ))
//                        val intent = Intent(context, WelcomeActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(intent)
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