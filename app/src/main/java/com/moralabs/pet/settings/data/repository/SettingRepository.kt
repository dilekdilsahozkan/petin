package com.moralabs.pet.settings.data.repository

import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.data.remote.dto.SettingsRequestDto
import retrofit2.Response

interface SettingRepository {
    suspend fun logout(logout: SettingsRequestDto): Response<SettingsDto>
}