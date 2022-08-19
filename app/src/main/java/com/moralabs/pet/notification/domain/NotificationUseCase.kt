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

    fun notificationPet(): Flow<BaseResult<NotificationDto>> {
        return flow {
            var notification = notificationRepository.notificationPet().body().let {
                NotificationDto(
                    text = it?.data?.text,
                    type = it?.data?.type,
                    contentId = it?.data?.contentId,
                    dateTime = it?.data?.dateTime,
                    pageIndex = it?.data?.pageIndex
                )
            }
            emit(
                BaseResult.Success(
                    notification
                )
            )
        }
    }

    fun notificationDateTime(): Flow<BaseResult<NotificationDto>> {
        return flow {
            var notificationTime = notificationRepository.notificationDateTime().body().let {
                NotificationDto(
                    text = it?.data?.text,
                    type = it?.data?.type,
                    contentId = it?.data?.contentId,
                    dateTime = it?.data?.dateTime,
                    pageIndex = it?.data?.pageIndex
                )
            }
            emit(
                BaseResult.Success(
                    notificationTime
                )
            )
        }
    }
}