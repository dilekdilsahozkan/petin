package com.moralabs.pet.petProfile.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.EmptyDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.data.remote.dto.PetRequestDto
import retrofit2.Response
import retrofit2.http.*

interface PetService {

    @GET("/pet")
    suspend fun getPet(): Response<BaseResponse<List<PetDto>>>

    @GET("/pet/{petId}")
    suspend fun petInfo(@Path("petId") petId: String?): Response<BaseResponse<PetDto>>

    @POST("/pet")
    suspend fun addPet(@Body addPet: PetRequestDto?): Response<BaseResponse<PetDto>>

    @DELETE("/pet/{petId}")
    suspend fun deletePet(@Path("petId") petId: String?): Response<EmptyDto>

    @PUT("/pet/{petId}")
    suspend fun editPet(@Body editPet: PetRequestDto?, @Path("petId") petId: String?): Response<BaseResponse<PetDto>>

    @GET("/pet/user/{userId}")
    suspend fun getAnotherUserPet(@Path("userId") userId: String?): Response<BaseResponse<List<PetDto>>>

    @GET("/pet/{petId}/user/{userId}")
    suspend fun getAnotherUserPetInfo(
        @Path("petId") petId: String?,
        @Path("userId") userId: String?
    ): Response<BaseResponse<PetDto>>

}