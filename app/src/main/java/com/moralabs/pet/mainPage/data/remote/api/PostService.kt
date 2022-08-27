package com.moralabs.pet.mainPage.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface PostService {
    @GET("/feed")
    suspend fun getFeed(): Response<BaseResponse<PostDto>>

    @GET("/feed/post")
    suspend fun getPost(): Response<BaseResponse<PostDto>>

    @POST("/feed/post/{postId}/{dateTime}")
    suspend fun postDateTime(): Response<BaseResponse<PostDto>>
}