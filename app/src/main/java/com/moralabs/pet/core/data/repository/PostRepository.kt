package com.moralabs.pet.core.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import retrofit2.Response

interface PostRepository {
    suspend fun getFeed(): Response<BaseResponse<List<PostDto>>>
    suspend fun postFeed(newPost: NewPostDto): Response<BaseResponse<List<PostDto>>>
    suspend fun likePost(postId: String?): Response<BaseResponse<List<PostDto>>>
    suspend fun unlikePost(postId: String?): Response<BaseResponse<List<PostDto>>>
    suspend fun profilePost(): Response<BaseResponse<List<PostDto>>>
    suspend fun getPostAnotherUser(userId: String?): Response<BaseResponse<List<PostDto>>>
}