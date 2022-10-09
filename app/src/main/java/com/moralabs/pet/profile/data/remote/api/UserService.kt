package com.moralabs.pet.profile.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("/user")
    suspend fun userInfo(): Response<BaseResponse<UserDto>>

    @POST("/user")
    suspend fun searchUser(@Query("keyword") keyword: String?): Response<BaseResponse<List<UserDto>>>

    @GET("/user/{userId}")
    suspend fun otherUsersInfo(@Path("userId") userId: String?): Response<BaseResponse<UserDto>>

    @GET("/user/followed")
    suspend fun getFollowedList() : Response<BaseResponse<List<UserInfoDto>>>

    @GET("/user/follower")
    suspend fun getFollowerList() : Response<BaseResponse<List<UserInfoDto>>>

    @POST("/user/{userId}/follow")
    suspend fun followUser(@Path("userId") userId: String?): Response<BaseResponse<Nothing>>

    @PATCH("/user/{userId}/unfollow")
    suspend fun unfollowUser(@Path("userId") userId: String?): Response<BaseResponse<Nothing>>

    @GET("/user/blocked")
    suspend fun getBlockedList() : Response<BaseResponse<List<UserInfoDto>>>

    @POST("/user/{userId}/block")
    suspend fun blockUser(@Path("userId") userId: String?): Response<BaseResponse<Nothing>>

    @PATCH("/user/{userId}/unblock")
    suspend fun unblockUser(@Path("userId") userId: String?): Response<BaseResponse<Nothing>>
}