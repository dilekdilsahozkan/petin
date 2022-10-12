package com.moralabs.pet.notification.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import com.moralabs.pet.notification.data.remote.dto.NotificationTokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationService {
    @GET("/notification")
    suspend fun notificationPet(): Response<BaseResponse<List<NotificationDto>>>

    @GET("/notification/{dateTime}")
    suspend fun notificationDateTime(@Path("dateTime") dateTime: String?): Response<BaseResponse<List<NotificationDto>>>

    @GET("/notification/latest")
    suspend fun latestNotification(): Response<BaseResponse<Boolean>>

    @POST("/user/notification/token")
    suspend fun sendToken(@Body request: NotificationTokenDto): Response<BaseResponse<Nothing>>
}