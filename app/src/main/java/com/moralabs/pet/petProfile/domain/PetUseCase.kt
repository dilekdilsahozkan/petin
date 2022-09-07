package com.moralabs.pet.petProfile.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.petProfile.data.remote.dto.CreatePetDto
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.data.remote.dto.PetRequestDto
import com.moralabs.pet.petProfile.data.repository.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PetUseCase @Inject constructor(
    private val petRepository: PetRepository
) : BaseUseCase() {

    fun petPost(): Flow<BaseResult<List<PetDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    petRepository.petPost().body()?.data ?: listOf()
                )
            )
        }
    }

    fun petInfo(petId: String?): Flow<BaseResult<PetAttributeDto>> {
        return flow {
            val result = petRepository.petInfo(petId).body()?.data.let {
                PetAttributeDto(
                    attributeId = it?.attributeId,
                    attributeName = it?.attributeName,
                    type = it?.type,
                    choice = it?.choice
                )
            }
            emit(
                BaseResult.Success(
                    result
                )
            )
        }
    }

    fun addPet(addPet: PetRequestDto): Flow<BaseResult<List<PetDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    petRepository.addPet(addPet).body()?.data ?: listOf()
                )
            )
        }
    }
}