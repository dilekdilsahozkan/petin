package com.moralabs.pet.petProfile.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response
import retrofit2.http.GET

interface PetService {

    @GET("/pet")
    suspend fun getPet(): Response<BaseResponse<List<PetDto>>>
}