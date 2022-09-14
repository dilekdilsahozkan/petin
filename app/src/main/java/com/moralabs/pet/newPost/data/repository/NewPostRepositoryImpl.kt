package com.moralabs.pet.newPost.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.newPost.data.remote.api.NewPostService
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class NewPostRepositoryImpl @Inject constructor(private val service: NewPostService)
    : NewPostRepository, BaseRepository {
    override suspend fun createPost(createNewPost: NewPostDto): Response<BaseResponse<List<PetDto>>> = service.createPost(createNewPost)
}