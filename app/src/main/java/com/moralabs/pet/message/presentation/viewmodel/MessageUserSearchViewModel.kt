package com.moralabs.pet.message.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.extension.isNotEmptyOrBlank
import com.moralabs.pet.message.domain.MessageUseCase
import com.moralabs.pet.profile.data.remote.dto.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageUserSearchViewModel @Inject constructor(
    private val useCase: MessageUseCase
) : BaseViewModel<List<UserDto>>(useCase) {

    private var job: Job? = null

    fun searchUser(keyword: String) {
        job?.cancel()

        if (keyword.isNotEmptyOrBlank()) {
            job = viewModelScope.launch {
                useCase.searchUser(keyword)
                    .onStart {
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
                            }
                        }
                    }
            }
        } else {
            _state.value = ViewState.Success(listOf())
        }
    }
}