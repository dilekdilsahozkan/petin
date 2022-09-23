package com.moralabs.pet.settings.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.data.remote.dto.BlockedDto
import com.moralabs.pet.settings.data.remote.dto.ChangePasswordRequestDto
import com.moralabs.pet.settings.data.remote.dto.EditUserDto
import com.moralabs.pet.settings.domain.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase
) : BaseViewModel<UserDto>(useCase) {

    private var _stateBlocked: MutableStateFlow<ViewState<List<BlockedDto>>> = MutableStateFlow(ViewState.Idle())
    val stateBlocked: StateFlow<ViewState<List<BlockedDto>>> = _stateBlocked

    private var _stateUnBlocked: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val stateUnBlocked: StateFlow<ViewState<Boolean>> = _stateUnBlocked

    private var _stateLiked: MutableStateFlow<ViewState<List<PostDto>>> = MutableStateFlow(ViewState.Idle())
    val stateLiked: StateFlow<ViewState<List<PostDto>>> = _stateLiked

    private var _stateChangePW: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val stateChangePW: StateFlow<ViewState<Boolean>> = _stateChangePW

    private var _stateInfo: MutableStateFlow<ViewState<String>> = MutableStateFlow(ViewState.Idle())
    val stateInfo: StateFlow<ViewState<String>> = _stateInfo

    fun logout() {
        useCase.logout()
    }

    fun userInfo() {
        viewModelScope.launch {
            useCase.userInfo()
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _state.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }

    fun editUser(edit: EditUserDto) {
        viewModelScope.launch {
            useCase.editUser(edit)
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _state.value = ViewState.Success(baseResult.data)
                    }
                }
        }
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

    fun getLikedPosts() {
        viewModelScope.launch {
            useCase.getLikedPosts()
                .onStart {
                    _stateLiked.value = ViewState.Loading()
                }
                .catch { exception ->
                    _stateLiked.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _stateLiked.value = ViewState.Idle()
                        _stateLiked.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }

    fun changePassword(refreshToken: String, changePassword: ChangePasswordRequestDto) {
        viewModelScope.launch {
            useCase.changePassword(refreshToken, changePassword)
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _stateChangePW.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }

    fun getInfo(infoType: Int) {
        viewModelScope.launch {
            useCase.getInfo(infoType)
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _stateInfo.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }
}