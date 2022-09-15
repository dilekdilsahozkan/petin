package com.moralabs.pet.core.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response
import retrofit2.http.Path

interface PostRepository {
    suspend fun getFeed(): Response<BaseResponse<List<PostDto>>>
    suspend fun postFeed(newPost: NewPostDto): Response<BaseResponse<List<PostDto>>>
    suspend fun getPetProfile(petId: String?, userId: String?): Response<BaseResponse<PetDto>>
    suspend fun likePost(postId: String?): Response<BaseResponse<Nothing>>
    suspend fun unlikePost(postId: String?): Response<BaseResponse<Nothing>>
    suspend fun profilePost(): Response<BaseResponse<List<PostDto>>>
    suspend fun getPostAnotherUser(userId: String?): Response<BaseResponse<List<PostDto>>>
    suspend fun deletePost(postId: String?): Response<BaseResponse<Nothing>>
}