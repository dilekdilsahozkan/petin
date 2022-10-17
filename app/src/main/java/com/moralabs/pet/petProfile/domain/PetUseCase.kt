package com.moralabs.pet.petProfile.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.MediaRepository
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
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
            val pets = petRepository.petPost()
            if(pets.isSuccessful && pets.code() == 200){
                emit(
                    BaseResult.Success(pets.body()?.data?: listOf())
                )
            }else{
                val error = Gson().fromJson(pets.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun petInfo(petId: String?): Flow<BaseResult<PetDto>> {
        return flow {
            val petInfo =  petRepository.petInfo(petId)
            if(petInfo.isSuccessful && petInfo.code() == 200){
                petInfo.body()?.data?.let {
                    emit(BaseResult.Success(it))
                }
            }else{
                val error = Gson().fromJson(petInfo.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun deletePet(petId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            val delete = petRepository.deletePet(petId)
            if(delete.isSuccessful && delete.code() == 200){
                emit(BaseResult.Success(delete.isSuccessful))
            }else{
                val error = Gson().fromJson(delete.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun petAttributes(): Flow<BaseResult<List<AttributeDto>>> {
        return flow {
            val attributes = petRepository.petAttributes()
            if(attributes.isSuccessful && attributes.code() == 200){
                emit(
                    BaseResult.Success(
                        attributes.body()?.data ?: listOf()
                    )
                )
            }else{
                val error = Gson().fromJson(attributes.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun savePet(file: File?, name: String?, attributes: List<PetAttributeDto>): Flow<BaseResult<Boolean>> {
        return flow {

            val postDto = PetRequestDto(
                name = name,
                petAttributes = attributes
            )

            val medias = mutableListOf<MediaDto>()

            file?.let {
                val media = mediaRepository.uploadPhoto(1, it)

                media.body()?.data?.getOrNull(0)?.let {
                    medias.add(it)
                }
            }

            if (medias.isNotEmpty()) {
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

    fun updatePet(petDto: PetDto, file: File?, name: String?, attributes: List<PetAttributeDto>): Flow<BaseResult<Boolean>> {
        return flow {

            val postDto = PetRequestDto(
                name = name,
                petAttributes = attributes
            )

            val medias = mutableListOf<MediaDto>()

            file?.let {
                val media = mediaRepository.uploadPhoto(1, it)

                media.body()?.data?.getOrNull(0)?.let {
                    medias.add(it)
                }
            } ?: run {
                petDto.media?.let {
                    medias.add(it)
                }
            }

            if (medias.isNotEmpty()) {
                postDto.media = medias
            }

            val result = petRepository.updatePet(petDto.id, postDto).body()?.success ?: false

            if (result) {
                emit(BaseResult.Success(false))
            } else {
                emit(BaseResult.Error(ErrorResult(code = ErrorCode.SERVER_ERROR)))
            }
        }
    }

    fun getAnotherUserPet(userId: String?): Flow<BaseResult<List<PetDto>>> {
        return flow {
            val another = petRepository.getAnotherUserPet(userId)
            if(another.isSuccessful && another.code() == 200){
                emit(
                    BaseResult.Success(
                        another.body()?.data ?: listOf()
                    )
                )
            }else{
                val error = Gson().fromJson(another.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun getAnotherUserPetInfo(petId: String?, userId: String?): Flow<BaseResult<PetDto>> {
        return flow {
           val anotherPet =  petRepository.getAnotherUserPetInfo(petId, userId)
               if(anotherPet.isSuccessful && anotherPet.code() == 200){
                   anotherPet.body()?.data?.let {
                       emit(BaseResult.Success(it))
                   }
               }else{
                   val error = Gson().fromJson(anotherPet.errorBody()?.string(), BaseResponse::class.java)
                   emit(
                       BaseResult.Error(
                           ErrorResult(
                               code = ErrorCode.SERVER_ERROR,
                               error.userMessage
                           )
                       )
                   )
               }
        }
    }
}