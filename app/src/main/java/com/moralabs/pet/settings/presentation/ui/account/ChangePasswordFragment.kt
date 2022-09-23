package com.moralabs.pet.settings.presentation.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.AuthenticationDto
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentChangePasswordBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.data.remote.dto.ChangePasswordRequestDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding, UserDto, SettingsViewModel>() {

    companion object {
        private const val USER_KEY = "user"
    }

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

    override fun getLayoutId() = R.layout.fragment_change_password
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_change_password))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val refreshToken = preferences?.getString(USER_KEY, "")
        val json = Gson().fromJson(refreshToken, AuthenticationDto::class.java)

        binding.editProfile.setOnClickListener {
            viewModel.changePassword(
                json.refreshKey ?: "",
                ChangePasswordRequestDto(
                    oldPassword = binding.oldPassword.text.toString(),
                    newPassword = binding.newPassword.text.toString()
                )
            )
        }
    }
}