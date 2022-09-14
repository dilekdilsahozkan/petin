package com.moralabs.pet.mainPage.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
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
                            _state.value = ViewState.Error(message = baseResult.error.message)
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
                    if (baseResult is BaseResult.Success) {
                        _state.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }
}