package com.moralabs.pet.onboarding.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.onboarding.data.remote.dto.*
import com.moralabs.pet.onboarding.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase
) : BaseViewModel<LoginDto>(useCase) {

    private var _forgotState: MutableStateFlow<ViewState<Any>> =
        MutableStateFlow(ViewState.Idle())
    val forgotState: StateFlow<ViewState<Any>> = _forgotState

    fun login(loginPet: LoginRequestDto) {
        viewModelScope.launch {
            useCase.login(loginPet)
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success ->
                            _state.value = ViewState.Success(baseResult.data)
                        is BaseResult.Error ->
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                    }
                }
        }
    }

    fun forgotPassword(sendEmail: ForgotPasswordDto) {
        viewModelScope.launch {
            useCase.forgotPassword(sendEmail)
                .onStart {
                    _forgotState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _forgotState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success ->
                            _forgotState.value = ViewState.Success(baseResult.data)
                        is BaseResult.Error ->
                            _forgotState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                    }
                }
        }
    }

    fun passwordCode(passwordCode: PasswordCodeDto) {
        viewModelScope.launch {
            useCase.passwordCode(passwordCode)
                .onStart {
                    _forgotState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _forgotState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success ->
                            _forgotState.value = ViewState.Success(baseResult.data)
                        is BaseResult.Error ->
                            _forgotState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                    }
                }
        }
    }

    fun newPassword(newPw: NewPasswordDto) {
        viewModelScope.launch {
            useCase.newPassword(newPw)
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _forgotState.value = ViewState.Idle()
                            _forgotState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _forgotState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun guestLogin() {
        viewModelScope.launch {
            useCase.guestLogin()
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _state.value = ViewState.Idle()
                            _state.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }
}