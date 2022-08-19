package com.moralabs.pet.notification.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import retrofit2.Response

interface NotificationRepository {
    suspend fun notificationPet(): Response<BaseResponse<NotificationDto>>
    suspend fun notificationDateTime(): Response<BaseResponse<NotificationDto>>
}