package com.moralabs.pet.newPost.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface NewPostService {
    @POST("/feed/post")
    suspend fun createPost(@Body createNewPost: NewPostDto): Response<BaseResponse<List<PetDto>>>

    @Multipart
    @Headers("Content-Type: multipart/form-data")
    @POST("/media")
    suspend fun uploadPhotos(@Part("type") key: Int? = 1, @Part image: List<MultipartBody.Part>): Response<BaseResponse<Any>>
}