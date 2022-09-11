package com.moralabs.pet.settings.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.settings.data.remote.api.SettingService
import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.data.remote.dto.SettingsRequestDto
import retrofit2.Response
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(private val service: SettingService) :
    SettingRepository, BaseRepository {
    override suspend fun logout(logout: SettingsRequestDto): Response<SettingsDto>  = service.logout(logout)
}