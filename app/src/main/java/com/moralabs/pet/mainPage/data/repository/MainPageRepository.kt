package com.moralabs.pet.mainPage.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import retrofit2.Response

interface MainPageRepository {
    suspend fun getFeed(): Response<BaseResponse<List<PostDto>>>
}