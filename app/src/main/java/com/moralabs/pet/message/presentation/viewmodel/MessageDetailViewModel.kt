package com.moralabs.pet.message.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.message.data.remote.dto.ChatDto
import com.moralabs.pet.message.data.remote.dto.ChatRequestDto
import com.moralabs.pet.message.domain.MessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageDetailViewModel @Inject constructor(
    private val useCase: MessageUseCase
) : BaseViewModel<ChatDto>(useCase) {

    fun getDetail(userId: String?) {
        viewModelScope.launch {
            useCase.getMessage(userId)
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
                            latestDto = baseResult.data
                            _state.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error()
                        }
                    }
                }
        }
    }

    fun sendMessage(userId: String?, message: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            useCase.sendMessage(userId, ChatRequestDto(text = message))
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
}