package com.moralabs.pet.notification.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.notification.data.remote.api.NotificationService
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import retrofit2.Response
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val service: NotificationService) :
    NotificationRepository, BaseRepository {
    override suspend fun notificationPet(): Response<BaseResponse<NotificationDto>> =
        service.notificationPet()

    override suspend fun notificationDateTime(): Response<BaseResponse<NotificationDto>> =
        service.notificationDateTime()
}