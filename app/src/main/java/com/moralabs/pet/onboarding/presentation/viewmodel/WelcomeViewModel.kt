package com.moralabs.pet.onboarding.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.onboarding.data.remote.dto.WelcomeDto
import com.moralabs.pet.onboarding.domain.WelcomeUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val useCase: WelcomeUseCase
): BaseViewModel<WelcomeDto>(useCase){

    fun welcomePet() {
        viewModelScope.launch {
            useCase.getWelcomePage()
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
                    }
                }
        }
    }
}