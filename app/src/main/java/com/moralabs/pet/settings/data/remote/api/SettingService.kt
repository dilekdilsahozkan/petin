package com.moralabs.pet.settings.data.remote.api

import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.data.remote.dto.SettingsRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SettingService {
    @POST("/auth/logout")
    suspend fun logout(@Body logout: SettingsRequestDto): Response<SettingsDto>
}