package com.moralabs.pet.settings.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.data.remote.dto.BlockedDto
import com.moralabs.pet.settings.data.remote.dto.ChangePasswordRequestDto
import com.moralabs.pet.settings.data.remote.dto.EditUserDto
import com.moralabs.pet.settings.data.remote.dto.SettingsRequestDto
import retrofit2.Response
import retrofit2.http.*

interface SettingService {
    @POST("/auth/logout")
    suspend fun logout(@Body logout: SettingsRequestDto): Response<BaseResponse<Nothing>>

    @GET("/user/blocked")
    suspend fun getBlockedAccounts(): Response<BaseResponse<List<BlockedDto>>>

    @PATCH("/user/{userId}/unblock")
    suspend fun unBlock(@Path("userId") userId: String?): Response<BaseResponse<Nothing>>

    @PUT("user")
    suspend fun editUser(@Body edit: EditUserDto): Response<BaseResponse<UserDto>>

    @GET("/feed/liked")
    suspend fun getLikedPosts(): Response<BaseResponse<List<PostDto>>>

    @PATCH("/auth/password")
    suspend fun changePassword(
        @Header("refreshToken") refreshToken: String,
        @Body changePassword: ChangePasswordRequestDto
    ): Response<Boolean>

    @GET("/info/{infoType}")
    suspend fun getInfo(@Path("infoType") infoType: Int?): Response<BaseResponse<String>>

    @DELETE("/user")
    suspend fun deleteAccount(): Response<BaseResponse<Nothing>>
}