package com.moralabs.pet.newPost.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.mainPage.data.remote.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface NewPostService {
    @GET("/feed/post")
    suspend fun mainPagePost(): Response<BaseResponse<PostDto>>
}