package com.moralabs.pet.mainPage.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.mainPage.data.remote.dto.CommentRequestDto
import com.moralabs.pet.mainPage.domain.CommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val useCase: CommentUseCase
) : BaseViewModel<CommentDto>(useCase) {

    protected var _deleteState: MutableStateFlow<ViewState<Boolean>> =
        MutableStateFlow(ViewState.Idle())
    val deleteState: StateFlow<ViewState<Boolean>> = _deleteState

    fun writeComments(postId: String?, comment: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            useCase.writeComment(postId, CommentRequestDto(text = comment))
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
                            onSuccess()
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun getComment(postId: String?) {
        viewModelScope.launch {
            useCase.getComments(postId)
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

    fun deleteComment(commentId: String?) {
        viewModelScope.launch {
            useCase.deleteComment(commentId)
                .onStart {
                    _deleteState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _deleteState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _deleteState.value = ViewState.Idle()
                            _deleteState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _deleteState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }
}