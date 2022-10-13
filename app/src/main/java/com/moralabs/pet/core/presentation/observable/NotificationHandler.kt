package com.moralabs.pet.core.presentation.observable

import androidx.lifecycle.MutableLiveData
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.notification.domain.NotificationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotificationHandler(private val useCase: NotificationUseCase) {

    private var currentRequest: Job? = null

    private val _hasNotification: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasNotification = _hasNotification

    fun checkNotifications() {
        currentRequest?.cancel()

        currentRequest = CoroutineScope(Dispatchers.Default).launch {
            useCase.latestNotification().collect {
                when (it) {
                    is BaseResult.Success<Boolean> -> {
                        _hasNotification.postValue(it.data)
                    }
                }
            }
        }
    }

    fun clearNotification() {
        _hasNotification.postValue(false)
    }
}