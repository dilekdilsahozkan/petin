package com.moralabs.pet.core.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response

interface PostRepository {
    suspend fun getFeed(
        searchQuery: String? = null,
        index: Int? = null,
        size: Int? = null,
        dateTime: Long? = null, 
        postType: Int? = null
    ): Response<BaseResponse<List<PostDto>>>

    suspend fun postFeed(newPost: NewPostDto): Response<BaseResponse<List<PostDto>>>
    suspend fun getPetProfile(petId: String?, userId: String?): Response<BaseResponse<PetDto>>
    suspend fun likePost(postId: String?): Response<BaseResponse<Nothing>>
    suspend fun unlikePost(postId: String?): Response<BaseResponse<Nothing>>
    suspend fun reportPost(postId: String?, reportType: Int?): Response<BaseResponse<Nothing>>
    suspend fun profilePost(): Response<BaseResponse<List<PostDto>>>
    suspend fun getPostAnotherUser(userId: String?): Response<BaseResponse<List<PostDto>>>
    suspend fun deletePost(postId: String?): Response<BaseResponse<Nothing>>
}