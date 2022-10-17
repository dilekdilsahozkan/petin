package com.moralabs.pet.profile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.profile.domain.ProfilePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePostViewModel @Inject constructor(
    private val useCase: ProfilePostUseCase
) : BaseViewModel<List<PostDto>>(useCase) {

    protected var _likeUnlikeDeleteState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val likeUnlikeDeleteState: StateFlow<ViewState<Boolean>> = _likeUnlikeDeleteState

    fun profilePost() {
        viewModelScope.launch {
            useCase.profilePost()
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

    fun getPostAnotherUser(userId: String?) {
        viewModelScope.launch {
            useCase.getPostAnotherUser(userId)
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

    fun likePost(postId: String?) {
        viewModelScope.launch {
            useCase.likePost(postId)
                .onStart {
                    _likeUnlikeDeleteState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _likeUnlikeDeleteState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _likeUnlikeDeleteState.value = ViewState.Success(baseResult.data)
                    }
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _state.value = ViewState.Idle()
                            _likeUnlikeDeleteState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
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
                            _state.value = ViewState.Idle()
                            _likeUnlikeDeleteState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun deletePost(postId: String?) {
        viewModelScope.launch {
            useCase.deletePost(postId)
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
                            _state.value = ViewState.Idle()
                            _likeUnlikeDeleteState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }
}