package com.moralabs.pet.newPost.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.newPost.data.repository.NewPostRepository
import com.moralabs.pet.petProfile.data.remote.dto.CreatePostDto
import com.moralabs.pet.petProfile.data.repository.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewPostUseCase @Inject constructor(
    private val newPostRepository: NewPostRepository,
    private val petRepository: PetRepository
) : BaseUseCase() {
    fun newPost(createNewPost: NewPostDto): Flow<BaseResult<CreatePostDto>> {
        return flow {
            val postValue = newPostRepository.createPost(createNewPost).body()?.data ?: listOf()

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
}