package com.moralabs.pet.notification.presentation.viewmodel

import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.notification.data.remote.dto.GroupNotification
import com.moralabs.pet.notification.domain.NotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    useCase: NotificationUseCase
) : BaseViewModel<GroupNotification>(useCase) {

}