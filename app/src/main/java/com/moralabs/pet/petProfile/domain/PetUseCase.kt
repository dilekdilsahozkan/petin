package com.moralabs.pet.petProfile.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.petProfile.data.remote.dto.AttributeDto
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

    fun petInfo(petId: String?): Flow<BaseResult<PetDto>> {
        return flow {
            petRepository.petInfo(petId).body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }
        }
    }

    fun addPet(addPet: PetRequestDto): Flow<BaseResult<PetDto>> {
        return flow {
            petRepository.addPet(addPet).body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }
        }
    }

    fun editPet(editPet: PetRequestDto, petId: String?): Flow<BaseResult<PetDto>> {
        return flow {
            petRepository.editPet(editPet, petId).body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }
        }
    }

    fun deletePet(petId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            emit(
                BaseResult.Success(
                    petRepository.deletePet(petId).isSuccessful
                )
            )
        }
    }

    fun petAttributes(): Flow<BaseResult<List<AttributeDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    petRepository.petAttributes().body()?.data ?: listOf()
                )
            )
        }
    }

    fun getAnotherUserPet(userId: String?): Flow<BaseResult<List<PetDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    petRepository.getAnotherUserPet(userId).body()?.data ?: listOf()
                )
            )
        }
    }

    fun getAnotherUserPetInfo(petId: String?, userId: String?): Flow<BaseResult<PetDto>> {
        return flow {
            petRepository.getAnotherUserPetInfo(petId, userId).body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }
        }
    }
}