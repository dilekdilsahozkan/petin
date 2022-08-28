package com.moralabs.pet.profile.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import retrofit2.Response

interface ProfilePostRepository {
    suspend fun profilePost(): Response<BaseResponse<List<PostDto>>>
}