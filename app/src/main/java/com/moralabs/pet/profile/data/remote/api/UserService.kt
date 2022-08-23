package com.moralabs.pet.profile.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.profile.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET

interface UserService {

    @GET("/user")
    suspend fun userInfo(): Response<BaseResponse<UserDto>>

    @DELETE("user")
    suspend fun deleteUser(): Response<Any>

    @GET("/user/{userId}")
    suspend fun otherUsersInfo(): Response<BaseResponse<UserDto>>
}