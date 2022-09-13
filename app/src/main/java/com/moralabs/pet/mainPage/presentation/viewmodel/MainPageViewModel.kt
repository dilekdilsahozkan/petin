package com.moralabs.pet.mainPage.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.mainPage.domain.MainPageUseCase
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val useCase: MainPageUseCase
) : BaseViewModel<List<PostDto>>(useCase) {

    protected var _getPetState: MutableStateFlow<ViewState<PetDto>> = MutableStateFlow(ViewState.Idle())
    val getPetState: StateFlow<ViewState<PetDto>> = _getPetState

    fun feedPost() {
        viewModelScope.launch {
            useCase.getFeed()
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

    fun getPetProfile(petId: String?, userId: String?) {
        viewModelScope.launch {
            useCase.getPetProfile(petId, userId)
                .onStart {
                    _getPetState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _getPetState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _getPetState.value = ViewState.Idle()
                    }
                }
        }
    }

    fun likePost(postId: String?) {
        viewModelScope.launch {
            useCase.likePost(postId)
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _state.value = ViewState.Idle()
                    }
                }
        }
    }
}