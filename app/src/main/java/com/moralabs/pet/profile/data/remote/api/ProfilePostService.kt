package com.moralabs.pet.profile.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface ProfilePostService {

    @GET("/feed/post")
    suspend fun getPost(): Response<BaseResponse<List<PostDto>>>
}