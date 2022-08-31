package com.moralabs.pet.petProfile.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response

interface PetRepository {
    suspend fun petPost(): Response<BaseResponse<List<PetDto>>>
}