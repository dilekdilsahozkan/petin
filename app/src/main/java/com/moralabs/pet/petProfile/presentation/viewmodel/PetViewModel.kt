package com.moralabs.pet.petProfile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.data.remote.dto.PetRequestDto
import com.moralabs.pet.petProfile.domain.PetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val useCase: PetUseCase
): BaseViewModel<List<PetDto>>(useCase){

    protected var _addState: MutableStateFlow<ViewState<PetDto>> =
        MutableStateFlow(ViewState.Idle())
    val addState: StateFlow<ViewState<PetDto>> = _addState

    fun getPet(){
        viewModelScope.launch {
            useCase.petPost()
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

    fun getAnotherUserPet(userId: String?){
        viewModelScope.launch {
            useCase.getAnotherUserPet(userId)
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

    fun addPet(addPet: PetRequestDto){
        viewModelScope.launch {
            useCase.addPet(addPet)
                .onStart {
                    _addState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _addState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _addState.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }
}