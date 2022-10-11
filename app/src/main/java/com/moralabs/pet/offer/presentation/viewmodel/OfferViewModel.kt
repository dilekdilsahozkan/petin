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

    protected var _declineState: MutableStateFlow<ViewState<Boolean>> =
        MutableStateFlow(ViewState.Idle())
    val declineState: StateFlow<ViewState<Boolean>> = _declineState

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

    fun declineOffer(offerId: String?) {
        viewModelScope.launch {
            useCase.declineOffer(offerId)
                .onStart {
                    _declineState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _declineState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _declineState.value = ViewState.Idle()
                            _declineState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _declineState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
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
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _acceptState.value = ViewState.Idle()
                            _acceptState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _acceptState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }
}