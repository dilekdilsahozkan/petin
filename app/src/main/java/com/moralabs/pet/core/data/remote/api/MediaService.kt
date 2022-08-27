package com.moralabs.pet.core.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.MediaDto
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.PUT

interface MediaService {
    @POST("/media")
    suspend fun uploadMedia(): Response<BaseResponse<MediaDto>>

    @PUT("/media/{mediaId}")
    suspend fun uploadToCloud(): Response<BaseResponse<MediaDto>>
}