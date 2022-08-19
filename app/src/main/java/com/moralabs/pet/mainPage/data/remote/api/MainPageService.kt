package com.moralabs.pet.mainPage.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.mainPage.data.remote.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface MainPageService {

    @GET("/feed/post")
    suspend fun mainPagePost(): Response<BaseResponse<PostDto>>

    @POST("/feed/post/{postId}/{dateTime}")
    suspend fun postDateTime(): Response<BaseResponse<PostDto>>
}