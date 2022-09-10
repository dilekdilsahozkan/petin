package com.moralabs.pet.notification.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import com.moralabs.pet.notification.data.repository.NotificationRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) : BaseUseCase() {

    fun notificationPet(): Flow<BaseResult<List<NotificationDto>>>{
        return flow {
            var notification = notificationRepository.notificationPet().body()?.data ?: listOf()
            emit(
                BaseResult.Success(
                    notification
                )
            )
        }
    }

    fun notificationDateTime(dateTime : String?): Flow<BaseResult<List<NotificationDto>>> {
        return flow {
            var notificationTime = notificationRepository.notificationDateTime(dateTime).body()?.data ?: listOf()
            emit(
                BaseResult.Success(
                    notificationTime
                )
            )
        }
    }
}