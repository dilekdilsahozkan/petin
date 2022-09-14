package com.moralabs.pet.notification.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import retrofit2.Response

interface NotificationRepository {
    suspend fun notificationPet(): Response<BaseResponse<List<NotificationDto>>>
    suspend fun notificationDateTime(dateTime: String?): Response<BaseResponse<List<NotificationDto>>>
    suspend fun sendToken(token: String?): Response<BaseResponse<Nothing>>
    suspend fun getFirebaseToken(): String?
}