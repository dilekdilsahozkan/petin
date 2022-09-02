package com.moralabs.pet.newPost.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NewPostService {
    @POST("/feed/post")
    suspend fun createPost(@Body createNewPost: NewPostDto): Response<BaseResponse<List<PetDto>>>
}