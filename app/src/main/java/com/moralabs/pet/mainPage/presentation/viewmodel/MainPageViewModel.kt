package com.moralabs.pet.mainPage.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.mainPage.domain.MainPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val useCase: MainPageUseCase
) : BaseViewModel<List<PostDto>>(useCase) {

    private var job: Job? = null

    protected var _likeUnlikeDeleteState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val likeUnlikeDeleteState: StateFlow<ViewState<Boolean>> = _likeUnlikeDeleteState

    fun feedPost(searchQuery: String? = null) {
        job?.cancel()

        job = viewModelScope.launch {
            useCase.getFeed(searchQuery)
                .onStart {
                    if (searchQuery == null) _state.value = ViewState.Loading()
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
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _likeUnlikeDeleteState.value = ViewState.Idle()
                            _likeUnlikeDeleteState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _likeUnlikeDeleteState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
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
                            _likeUnlikeDeleteState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
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
                            _likeUnlikeDeleteState.value = ViewState.Idle()
                            _likeUnlikeDeleteState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _likeUnlikeDeleteState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }
}