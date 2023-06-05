package com.moralabs.pet.settings.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.data.remote.dto.BlockedDto
import com.moralabs.pet.settings.data.remote.dto.ChangePasswordRequestDto
import com.moralabs.pet.settings.data.remote.dto.SettingsRequestDto
import com.moralabs.pet.settings.domain.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase
) : BaseViewModel<UserDto>(useCase) {

    private var _stateBlocked: MutableStateFlow<ViewState<List<BlockedDto>>> =
        MutableStateFlow(ViewState.Idle())
    val stateBlocked: StateFlow<ViewState<List<BlockedDto>>> = _stateBlocked

    private var _editProfile: MutableStateFlow<ViewState<UserDto>> =
        MutableStateFlow(ViewState.Idle())
    val editProfile: StateFlow<ViewState<UserDto>> = _editProfile

    private var _stateUnBlocked: MutableStateFlow<ViewState<Boolean>> =
        MutableStateFlow(ViewState.Idle())
    val stateUnBlocked: StateFlow<ViewState<Boolean>> = _stateUnBlocked

    private var _stateLiked: MutableStateFlow<ViewState<List<PostDto>>> =
        MutableStateFlow(ViewState.Idle())
    val stateLiked: StateFlow<ViewState<List<PostDto>>> = _stateLiked

    private var _stateChangePW: MutableStateFlow<ViewState<Boolean>> =
        MutableStateFlow(ViewState.Idle())
    val stateChangePW: StateFlow<ViewState<Boolean>> = _stateChangePW

    private var _stateInfo: MutableStateFlow<ViewState<String>> = MutableStateFlow(ViewState.Idle())
    val stateInfo: StateFlow<ViewState<String>> = _stateInfo

    private var _stateDeleteAccount: MutableStateFlow<ViewState<Boolean>> =
        MutableStateFlow(ViewState.Idle())
    val stateDeleteAccount: StateFlow<ViewState<Boolean>> = _stateDeleteAccount

    private var _likeUnlikeDeleteState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val likeUnlikeDeleteState: StateFlow<ViewState<Boolean>> = _likeUnlikeDeleteState

    private var _logoutState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val logoutState: StateFlow<ViewState<Boolean>> = _logoutState

    var description: String? = ""

    fun logout(logout: SettingsRequestDto) {
        viewModelScope.launch {
            useCase.logout(logout)
                .onStart {
                    _logoutState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _logoutState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _logoutState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _logoutState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
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

    fun editUser(name: String?, phoneNumber: String?, file: File?) {
        viewModelScope.launch {
            useCase.editUser(name, phoneNumber, file)
                .onStart {
                    _editProfile.value = ViewState.Loading()
                }
                .catch { exception ->
                    _editProfile.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _editProfile.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _editProfile.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
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
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _stateBlocked.value = ViewState.Idle()
                            _stateBlocked.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _stateBlocked.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
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
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _stateUnBlocked.value = ViewState.Idle()
                            _stateUnBlocked.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _stateUnBlocked.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
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
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _stateLiked.value = ViewState.Idle()
                            _stateLiked.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _stateLiked.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun changePassword(refreshToken: String, changePassword: ChangePasswordRequestDto) {
        viewModelScope.launch {
            useCase.changePassword(refreshToken, changePassword)
                .onStart {
                    _stateChangePW.value = ViewState.Loading()
                }
                .catch { exception ->
                    _stateChangePW.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _stateChangePW.value = ViewState.Idle()
                            _stateChangePW.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _stateChangePW.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
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
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _stateInfo.value = ViewState.Idle()
                            _stateInfo.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _stateInfo.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            useCase.deleteAccount()
                .onStart {
                    _stateDeleteAccount.value = ViewState.Loading()
                }
                .catch { exception ->
                    _stateDeleteAccount.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _stateDeleteAccount.value = ViewState.Idle()
                            _stateDeleteAccount.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _stateDeleteAccount.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun unlikePost(postId: String?) {
        viewModelScope.launch {
            useCase.unlikePost(postId)
                .onStart {
                    _likeUnlikeDeleteState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _likeUnlikeDeleteState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _likeUnlikeDeleteState.value = ViewState.Idle()
                            _likeUnlikeDeleteState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _stateChangePW.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }

        }
    }
}