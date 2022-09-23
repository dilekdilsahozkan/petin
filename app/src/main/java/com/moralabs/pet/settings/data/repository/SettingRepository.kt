package com.moralabs.pet.settings.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.data.remote.dto.BlockedDto
import com.moralabs.pet.settings.data.remote.dto.ChangePasswordRequestDto
import com.moralabs.pet.settings.data.remote.dto.EditUserDto
import com.moralabs.pet.settings.data.remote.dto.SettingsRequestDto
import retrofit2.Response

interface SettingRepository {
    suspend fun logout(logout: SettingsRequestDto): Response<BaseResponse<Nothing>>
    suspend fun getBlockedAccounts(): Response<BaseResponse<List<BlockedDto>>>
    suspend fun unBlock(userId: String?): Response<BaseResponse<Nothing>>
    suspend fun editUser(edit: EditUserDto): Response<BaseResponse<UserDto>>
    suspend fun getLikedPosts(): Response<BaseResponse<List<PostDto>>>
    suspend fun changePassword(refreshToken: String, changePassword: ChangePasswordRequestDto): Response<Boolean>
    suspend fun getInfo(infoType: Int): Response<BaseResponse<String>>
}