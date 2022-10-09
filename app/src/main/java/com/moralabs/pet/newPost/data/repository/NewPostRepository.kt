package com.moralabs.pet.newPost.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostLocationDto
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response

interface NewPostRepository {
    suspend fun createPost(createNewPost: NewPostDto): Response<BaseResponse<List<PetDto>>>
    suspend fun getLocation(): Response<BaseResponse<List<PostLocationDto>>>
}