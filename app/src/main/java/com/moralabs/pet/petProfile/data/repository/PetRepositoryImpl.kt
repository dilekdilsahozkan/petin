package com.moralabs.pet.petProfile.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.petProfile.data.remote.api.PetService
import com.moralabs.pet.petProfile.data.remote.dto.PetRequestDto
import javax.inject.Inject

class PetRepositoryImpl @Inject constructor(private val service: PetService) :
    PetRepository, BaseRepository {
    override suspend fun petPost() = service.getPet()
    override suspend fun petInfo(petId: String?) = service.petInfo(petId)
    override suspend fun addPet(addPet: PetRequestDto?) = service.addPet(addPet)
    override suspend fun editPet(editPet: PetRequestDto?, petId: String?) = service.editPet(editPet, petId)
    override suspend fun deletePet(petId: String?) = service.deletePet(petId)
    override suspend fun getAnotherUserPet(userId: String?) = service.getAnotherUserPet(userId)
    override suspend fun petAttributes() = service.petAttributes()
    override suspend fun getAnotherUserPetInfo(petId: String?, userId: String?) = service.getAnotherUserPetInfo(petId, userId)
}