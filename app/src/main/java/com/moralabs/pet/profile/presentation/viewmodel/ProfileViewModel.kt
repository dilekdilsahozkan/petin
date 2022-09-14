package com.moralabs.pet.profile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
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

    protected var _followState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val followState: StateFlow<ViewState<Boolean>> = _followState

    protected var _unfollowState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val unfollowState: StateFlow<ViewState<Boolean>> = _unfollowState

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
                    _followState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _followState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _followState.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }

    fun unfollowUser(userId: String?) {
        viewModelScope.launch {
            useCase.unfollowUser(userId)
                .onStart {
                    _unfollowState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _unfollowState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _unfollowState.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }
}