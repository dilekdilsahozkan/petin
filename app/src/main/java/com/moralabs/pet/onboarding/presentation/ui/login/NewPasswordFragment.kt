package com.moralabs.pet.onboarding.presentation.ui.login

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentNewPasswordBinding
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.NewPasswordDto
import com.moralabs.pet.onboarding.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewPasswordFragment : BaseFragment<FragmentNewPasswordBinding, LoginDto, LoginViewModel>() {

    private val passwordActivity by lazy {
        activity as? LoginActivity
    }

    override fun getLayoutId() = R.layout.fragment_new_password
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun addListeners() {
        super.addListeners()

        binding.editProfile.setOnClickListener {
            viewModel.newPassword(
                NewPasswordDto(
                    email = passwordActivity?.getPassword()?.email.toString(),
                    code = passwordActivity?.getPassword()?.code.toString(),
                    newPassword = binding.newPasswordEdit.text.toString()
                )
            )
        }
    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.forgotState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<*> -> {
                        startActivity(Intent(context, LoginActivity::class.java))
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.success_new_password),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is ViewState.Error<*> -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_new_password),
                            Toast.LENGTH_LONG
                        ).show()
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun fragmentViewModel(): BaseViewModel<LoginDto> {
        val viewModel: LoginViewModel by viewModels()
        return viewModel
    }
}