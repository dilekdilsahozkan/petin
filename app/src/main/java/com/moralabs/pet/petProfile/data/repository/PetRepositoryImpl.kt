package com.moralabs.pet.petProfile.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.petProfile.data.remote.api.PetService
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.data.remote.dto.PetRequestDto
import retrofit2.Response
import javax.inject.Inject

class PetRepositoryImpl  @Inject constructor(private val service: PetService) :
    PetRepository, BaseRepository {
    override suspend fun petPost(): Response<BaseResponse<List<PetDto>>> = service.getPet()
    override suspend fun petInfo(petId: String?): Response<BaseResponse<PetDto>> =service.petInfo(petId)
    override suspend fun addPet(addPet: PetRequestDto?): Response<BaseResponse<PetDto>> = service.addPet(addPet)
}