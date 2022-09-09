package com.moralabs.pet.core.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MediaService {
    @POST("/media")
    suspend fun uploadMedia(): Response<BaseResponse<List<MediaDto>>>

    @PUT("/media/{mediaId}")
    suspend fun uploadToCloud(@Path("mediaId") mediaId: String?): Response<BaseResponse<MediaDto>>
}