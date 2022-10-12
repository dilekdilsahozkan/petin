package com.moralabs.pet.onboarding.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.onboarding.data.remote.dto.RegisterDto
import com.moralabs.pet.onboarding.data.remote.dto.RegisterRequestDto
import com.moralabs.pet.onboarding.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: RegisterUseCase
): BaseViewModel<RegisterDto>(useCase){

    fun register(registerPet: RegisterRequestDto) {
        viewModelScope.launch {
            useCase.register(registerPet)
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success ->
                            _state.value = ViewState.Success(baseResult.data)
                        is BaseResult.Error ->
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                    }
                }
        }
    }
}