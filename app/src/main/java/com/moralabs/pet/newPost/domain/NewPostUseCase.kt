package com.moralabs.pet.newPost.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.newPost.data.repository.NewPostRepository
import com.moralabs.pet.petProfile.data.remote.dto.CreatePostDto
import com.moralabs.pet.petProfile.data.repository.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject


class NewPostUseCase @Inject constructor(
    private val newPostRepository: NewPostRepository,
    private val petRepository: PetRepository
) : BaseUseCase() {
    fun newPost(newPost: NewPostDto): Flow<BaseResult<CreatePostDto>> {
        return flow {

            var paths = mutableListOf<String>()
            val surveyImagesParts = mutableListOf<MultipartBody.Part>()

            var surveyBody = RequestBody.create("image/*".toMediaTypeOrNull(), newPost.files!!.get(0))
            surveyImagesParts.add(MultipartBody.Part.createFormData("PetImage", newPost?.files?.get(0)!!.name, surveyBody))

            val postImage = newPostRepository.upload(
//                MultipartBody.Part.createFormData(
//                    name = "pet_image",
//                    filename = newPost.files?.get(0)?.name,
//                    body = newPost?.files?.get(0)?.asRequestBody()!!
//                ), "pet_key_hehe"
            surveyImagesParts, ""
            )

            if (postImage.isSuccessful) {

            }

            val postValue = newPostRepository.createPost(newPost).body()?.data ?: listOf()
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