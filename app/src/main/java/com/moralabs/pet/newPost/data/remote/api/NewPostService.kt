package com.moralabs.pet.newPost.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NewPostService {
    @POST("/feed/post")
    suspend fun createPost(@Body createNewPost: NewPostDto): Response<BaseResponse<List<PostDto>>>
}