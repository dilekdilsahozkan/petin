package com.moralabs.pet.newPost.domain

import com.moralabs.pet.core.data.remote.dto.PostLocationDto
import com.moralabs.pet.core.data.repository.MediaRepository
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import com.moralabs.pet.newPost.data.remote.dto.MediaType
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.newPost.data.repository.NewPostRepository
import com.moralabs.pet.petProfile.data.remote.dto.CreatePostDto
import com.moralabs.pet.petProfile.data.repository.PetRepository
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewPostUseCase @Inject constructor(
    private val newPostRepository: NewPostRepository,
    private val petRepository: PetRepository,
    private val userRepository: ProfileRepository,
    private val mediaRepository: MediaRepository
) : BaseUseCase() {

    fun newPost(newPost: NewPostDto): Flow<BaseResult<CreatePostDto>> {
        return flow {

            val postDto = NewPostDto(
                text = newPost.text,
                type = newPost.type,
                locationId = newPost.locationId,
                petId = newPost.petId,
            )

            val medias = mutableListOf<MediaDto>()

            newPost.files?.forEach {
                val media = mediaRepository.uploadPhoto(MediaType.POST.value, it)

                media.body()?.data?.getOrNull(0)?.let{
                    medias.add(it)
                }
            }

            if(medias.isNotEmpty()){
                postDto.media = medias
            }

            val postValue = newPostRepository.createPost(postDto).body()?.data ?: listOf()
            emit(
                BaseResult.Success(
                    CreatePostDto(
                        postValue = postValue
                    )
                )
            )
        }
    }

    fun petValue(): Flow<BaseResult<CreatePostDto>> {
        return flow {
            val getValue = petRepository.petPost().body()?.data ?: listOf()
            emit(
                BaseResult.Success(
                    CreatePostDto(
                        getValue = getValue
                    )
                )
            )
        }
    }

    fun getLocation(): Flow<BaseResult<List<PostLocationDto>>> {
        return flow {
            newPostRepository.getLocation().body()?.data?.let {
                emit(BaseResult.Success(it))
            }

        }
    }

    fun userInfo(): Flow<BaseResult<UserDto>> {
        return flow {
            userRepository.userInfo().body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }
        }
    }
}