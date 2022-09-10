package com.moralabs.pet.notification.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationService {
    @GET("/notification")
    suspend fun notificationPet(): Response<BaseResponse<List<NotificationDto>>>

    @GET("/notification/{dateTime}")
    suspend fun notificationDateTime(@Path("dateTime") dateTime : String?): Response<BaseResponse<List<NotificationDto>>>
}