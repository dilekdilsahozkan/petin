package com.moralabs.pet.notification.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import com.moralabs.pet.notification.domain.NotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val  useCase: NotificationUseCase
) : BaseViewModel<List<NotificationDto>>(useCase) {

    var notification : MutableLiveData<NotificationDto?> = MutableLiveData(null)

    protected var _latestState: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle())
    val latestState: StateFlow<ViewState<Boolean>> = _latestState

    fun notificationPet(){
        viewModelScope.launch {
            useCase.notificationPet()
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

    fun latestNotification(){
        viewModelScope.launch {
            useCase.latestNotification()
                .onStart {
                    _latestState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _latestState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _latestState.value = ViewState.Idle()
                            _latestState.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _latestState.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    // TODO: Bu servis daha kullanılmadı
    fun notificationDateTime(dateTime : String?){
        viewModelScope.launch {
            useCase.notificationDateTime(dateTime)
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
}