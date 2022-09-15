package com.moralabs.pet.core.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import retrofit2.Response
import java.io.File

interface MediaRepository {
    suspend fun uploadPhoto(type: Int, media: File): Response<BaseResponse<List<MediaDto>>>
}