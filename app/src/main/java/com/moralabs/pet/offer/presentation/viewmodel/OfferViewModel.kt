package com.moralabs.pet.offer.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.offer.data.remote.dto.OfferDetailDto
import com.moralabs.pet.offer.domain.OfferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val useCase: OfferUseCase
) : BaseViewModel<OfferDetailDto>(useCase) {

    protected var _deleteState: MutableStateFlow<ViewState<Boolean>> =
        MutableStateFlow(ViewState.Idle())
    val deleteState: StateFlow<ViewState<Boolean>> = _deleteState

    protected var _acceptState: MutableStateFlow<ViewState<Boolean>> =
        MutableStateFlow(ViewState.Idle())
    val acceptState: StateFlow<ViewState<Boolean>> = _acceptState

    fun usersOffer(postId: String?) {
        viewModelScope.launch {
            useCase.usersOffer(postId)
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

    fun getOffer(offerId: String?) {
        viewModelScope.launch {
            useCase.getOffer(offerId)
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

    fun declineOffer(offerId: String?) {
        viewModelScope.launch {
            useCase.declineOffer(offerId)
                .onStart {
                    _deleteState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _deleteState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _deleteState.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }

    fun acceptOffer(offerId: String?) {
        viewModelScope.launch {
            useCase.acceptOffer(offerId)
                .onStart {
                    _acceptState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _acceptState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    if (baseResult is BaseResult.Success) {
                        _acceptState.value = ViewState.Success(baseResult.data)
                    }
                }
        }
    }
}