package com.moralabs.pet.profile.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.profile.data.remote.dto.PetDto
import retrofit2.Response

interface PetRepository {
    suspend fun petPost(): Response<BaseResponse<List<PetDto>>>
}