package com.moralabs.pet.newPost.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.data.remote.dto.PostLocationDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.newPost.domain.NewPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val useCase: NewPostUseCase
) : BaseViewModel<List<PostLocationDto>>(useCase) {

    fun getLocation(){
        viewModelScope.launch {
            useCase.getLocation()
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
