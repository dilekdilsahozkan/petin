package com.moralabs.pet.petProfile.domain

import com.moralabs.pet.core.data.repository.MediaRepository
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.petProfile.data.remote.dto.*
import com.moralabs.pet.petProfile.data.repository.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class PetUseCase @Inject constructor(
    private val petRepository: PetRepository,
    private val mediaRepository: MediaRepository
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

    fun savePet(file: File?, name: String?, attributes: List<PetPostAttributeDto>): Flow<BaseResult<Boolean>> {
        return flow {

            val postDto = PetRequestDto(
                name = name,
                petAttributes = attributes
            )

            val medias = mutableListOf<MediaDto>()

            file?.let {
                val media = mediaRepository.uploadPhoto(1, it)

                media.body()?.data?.getOrNull(0)?.let{
                    medias.add(it)
                }
            }

            if(medias.isNotEmpty()){
                postDto.media = medias
            }

            val result = petRepository.addPet(postDto).body()?.success ?: false

            if (result) {
                emit(BaseResult.Success(true))
            } else {
                emit(BaseResult.Error(ErrorResult(code = ErrorCode.SERVER_ERROR)))
            }

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