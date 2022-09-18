package com.moralabs.pet.settings.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.settings.data.remote.api.SettingService
import com.moralabs.pet.settings.data.remote.dto.EditUserDto
import com.moralabs.pet.settings.data.remote.dto.SettingsRequestDto
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(private val service: SettingService) :
    SettingRepository, BaseRepository {
    override suspend fun logout(logout: SettingsRequestDto) = service.logout(logout)
    override suspend fun getBlockedAccounts() = service.getBlockedAccounts()
    override suspend fun unBlock(userId: String?) = service.unBlock(userId)
    override suspend fun editUser(edit: EditUserDto) = service.editUser(edit)
}