package com.moralabs.pet.profile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import com.moralabs.pet.profile.domain.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase
) : BaseViewModel<UserDto>(useCase) {

    private var _followUserState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val followUserState: StateFlow<ViewState<Boolean>> = _followUserState

    private var _unfollowUserState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val unfollowUserState: StateFlow<ViewState<Boolean>> = _unfollowUserState

    private var _blockUserState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val blockUserState: StateFlow<ViewState<Boolean>> = _blockUserState

    private var _unblockUserState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val unblockUserState: StateFlow<ViewState<Boolean>> = _unblockUserState

    private var _reportState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val reportState: StateFlow<ViewState<Boolean>> = _reportState

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

    fun otherUsersInfo(userId: String?) {
        viewModelScope.launch {
            useCase.otherUsersInfo(userId)
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

    fun followUser(userId: String?) {
        viewModelScope.launch {
            useCase.followUser(userId)
                .onStart {
                    _followUserState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _followUserState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _state.value = ViewState.Idle()
                            _followUserState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun unfollowUser(userId: String?) {
        viewModelScope.launch {
            useCase.unfollowUser(userId)
                .onStart {
                    _unfollowUserState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _unfollowUserState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _state.value = ViewState.Idle()
                            _unfollowUserState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun blockUser(userId: String?) {
        viewModelScope.launch {
            useCase.blockUser(userId)
                .onStart {
                    _blockUserState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _blockUserState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _state.value = ViewState.Idle()
                            _blockUserState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun unblockUser(userId: String?) {
        viewModelScope.launch {
            useCase.unblockUser(userId)
                .onStart {
                    _unblockUserState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _unblockUserState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _state.value = ViewState.Idle()
                            _unblockUserState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun reportUser(userId: String?, reportType: Int?) {
        viewModelScope.launch {
            useCase.reportUser(userId, reportType)
                .onStart {
                    _reportState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _reportState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _state.value = ViewState.Idle()
                            _reportState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _reportState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }
}