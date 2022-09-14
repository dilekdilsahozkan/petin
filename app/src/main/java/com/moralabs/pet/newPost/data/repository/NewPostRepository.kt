package com.moralabs.pet.newPost.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.File

interface NewPostRepository {
    suspend fun createPost(createNewPost: NewPostDto): Response<BaseResponse<List<PetDto>>>
    suspend fun upload(file: List<MultipartBody.Part>, key: String) : Response<BaseResponse<Any>>
}