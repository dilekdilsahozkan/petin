package com.moralabs.pet.settings.presentation.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.FragmentDeleteAccountBinding
import com.moralabs.pet.onboarding.presentation.ui.welcome.WelcomeActivity
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeleteAccountFragment : BaseFragment<FragmentDeleteAccountBinding, UserDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_delete_account
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_delete_account))
    }

    override fun addListeners() {
        super.addListeners()

        binding.deleteAccountButton.setOnClickListener {
            PetWarningDialog(
                requireContext(),
                PetWarningDialogType.CONFIRMATION,
                resources.getString(R.string.ask_sure),
                okay = getString(R.string.yes),
                discard = getString(R.string.no),
                description = resources.getString(R.string.delete_user_warning),
                negativeButton = resources.getString(R.string.no),
                onResult = {
                    if (PetWarningDialogResult.OK == it) {
                        viewModel.deleteAccount()
                    }
                }
            ).show()
        }
    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.stateDeleteAccount.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<Boolean> -> {
                        if (it.data) {
                            startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                            activity?.finish()
                        }
                        stopLoading()
                    }
                    is ViewState.Error<*> -> {
                        stopLoading()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userInfo()
    }

    override fun stateSuccess(data: UserDto) {
        super.stateSuccess(data)

        binding.userName.text = data.userName
        binding.userImage.loadImage(data.media?.url)

    }
}