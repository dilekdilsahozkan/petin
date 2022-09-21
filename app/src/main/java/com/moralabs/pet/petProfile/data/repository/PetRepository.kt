package com.moralabs.pet.petProfile.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.EmptyDto
import com.moralabs.pet.petProfile.data.remote.dto.AttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.data.remote.dto.PetRequestDto
import retrofit2.Response

interface PetRepository {
    suspend fun petPost(): Response<BaseResponse<List<PetDto>>>
    suspend fun petInfo(petId: String?): Response<BaseResponse<PetDto>>
    suspend fun addPet(addPet: PetRequestDto?): Response<BaseResponse<PetDto>>
    suspend fun updatePet(petId: String?, requestDto: PetRequestDto?): Response<BaseResponse<PetDto>>
    suspend fun deletePet(petId: String?): Response<EmptyDto>
    suspend fun getAnotherUserPet(userId: String?): Response<BaseResponse<List<PetDto>>>
    suspend fun petAttributes(): Response<BaseResponse<List<AttributeDto>>>
    suspend fun getAnotherUserPetInfo(petId: String?, userId: String?): Response<BaseResponse<PetDto>>
}