package com.moralabs.pet.settings.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.AuthenticationUseCase
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.settings.data.remote.dto.BlockedDto
import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.domain.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase,
    private val authenticationUseCase: AuthenticationUseCase
) : BaseViewModel<SettingsDto>(useCase) {

    private var _stateBlocked: MutableStateFlow<ViewState<List<BlockedDto>>> = MutableStateFlow(ViewState.Idle())
    val stateBlocked: StateFlow<ViewState<List<BlockedDto>>> = _stateBlocked

    private var _stateUnBlocked: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val stateUnBlocked: StateFlow<ViewState<Boolean>> = _stateUnBlocked



    fun logout() {
        authenticationUseCase.logout()
    }

    fun getBlockedAccounts() {
        viewModelScope.launch {
            useCase.getBlockedAccounts()
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _state.value = ViewState.Idle()
                        _stateBlocked.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }

    fun unBlock(userId: String) {
        viewModelScope.launch {
            useCase.unBlock(userId)
                .onStart {
                    _stateUnBlocked.value = ViewState.Loading()
                }
                .catch { exception ->
                    _stateUnBlocked.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _stateUnBlocked.value = ViewState.Idle()
                        _stateUnBlocked.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }
}