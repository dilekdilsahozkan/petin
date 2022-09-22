package com.moralabs.pet.onboarding.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.data.remote.dto.AuthenticationDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
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

    private var _forgotState: MutableStateFlow<ViewState<Boolean>> =
        MutableStateFlow(ViewState.Idle())
    val forgotState: StateFlow<ViewState<Boolean>> = _forgotState

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
                            _state.value = ViewState.Error()
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
                            _forgotState.value = ViewState.Error()
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
                    if (baseResult is BaseResult.Success) {
                        _forgotState.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }

    fun newPassword(newPw: NewPasswordDto) {
        viewModelScope.launch {
            useCase.newPassword(newPw)
                .onStart {
                    _forgotState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _forgotState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _forgotState.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }
}