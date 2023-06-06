package com.moralabs.pet.core.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface MediaService {
    @Multipart
    @POST("/media")
    suspend fun uploadMedia(
        @Part media: MultipartBody.Part,
        @Part type: MultipartBody.Part,
    ): Response<BaseResponse<List<MediaDto>>>

    @PUT("/media/{mediaId}")
    suspend fun uploadToCloud(@Path("mediaId") mediaId: String?): Response<BaseResponse<MediaDto>>
}