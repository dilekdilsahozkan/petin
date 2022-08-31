package com.moralabs.pet.newPost.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import retrofit2.Response

interface NewPostRepository {
    suspend fun createPost(createNewPost: NewPostDto): Response<BaseResponse<List<PostDto>>>
}