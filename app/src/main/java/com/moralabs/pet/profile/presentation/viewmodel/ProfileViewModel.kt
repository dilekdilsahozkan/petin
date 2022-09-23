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

    protected var _followedListState: MutableStateFlow<ViewState<List<UserInfoDto>>> = MutableStateFlow(ViewState.Idle())
    val followedListState: StateFlow<ViewState<List<UserInfoDto>>> = _followedListState

    protected var _followUserState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val followUserState: StateFlow<ViewState<Boolean>> = _followUserState

    protected var _unfollowUserState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val unfollowUserState: StateFlow<ViewState<Boolean>> = _unfollowUserState

    protected var _blockedListState: MutableStateFlow<ViewState<List<UserInfoDto>>> = MutableStateFlow(ViewState.Idle())
    val blockedListState: StateFlow<ViewState<List<UserInfoDto>>> = _blockedListState

    protected var _blockUserState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val blockUserState: StateFlow<ViewState<Boolean>> = _blockUserState

    protected var _unblockUserState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val unblockUserState: StateFlow<ViewState<Boolean>> = _unblockUserState

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
                    if (baseResult is BaseResult.Success) {
                        _state.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }

    fun getFollowedList() {
        viewModelScope.launch {
            useCase.getFollowedList()
                .onStart {
                    _followedListState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _followedListState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _followedListState.value = ViewState.Success(baseResult.data)
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
                    if (baseResult is BaseResult.Success) {
                        _followUserState.value = ViewState.Success(baseResult.data)
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
                    if (baseResult is BaseResult.Success) {
                        _unfollowUserState.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }

    fun getBlockedList() {
        viewModelScope.launch {
            useCase.getBlockedList()
                .onStart {
                    _blockedListState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _blockedListState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _blockedListState.value = ViewState.Success(baseResult.data)
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
                    if (baseResult is BaseResult.Success) {
                        _blockUserState.value = ViewState.Success(baseResult.data)
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
                    if (baseResult is BaseResult.Success) {
                        _unblockUserState.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }

}