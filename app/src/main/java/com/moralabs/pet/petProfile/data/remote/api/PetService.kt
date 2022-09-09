package com.moralabs.pet.petProfile.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.data.remote.dto.PetRequestDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PetService {

    @GET("/pet")
    suspend fun getPet(): Response<BaseResponse<List<PetDto>>>

    @GET("/pet/{petId}")
    suspend fun petInfo(@Path("petId") petId: String?): Response<BaseResponse<PetDto>>

    @POST("/pet")
    suspend fun addPet(addPet: PetRequestDto?): Response<BaseResponse<PetDto>>
}