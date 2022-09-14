package com.moralabs.pet.profile.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.profile.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("/user")
    suspend fun userInfo(): Response<BaseResponse<UserDto>>

    @POST("/user")
    suspend fun searchUser(@Query("keyword") keyword: String?): Response<BaseResponse<List<UserDto>>>

    @DELETE("user")
    suspend fun deleteUser(): Response<Any>

    @GET("/user/{userId}")
    suspend fun otherUsersInfo(@Path("userId") userId: String?): Response<BaseResponse<UserDto>>
}