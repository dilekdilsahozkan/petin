package com.moralabs.pet.core.data.repository.impl

import com.moralabs.pet.core.data.remote.api.FeedService
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.core.data.repository.PostRepository
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val service: FeedService) :
    PostRepository, BaseRepository {
    override suspend fun getFeed(): Response<BaseResponse<List<PostDto>>> = service.getFeed()
    override suspend fun postFeed(newPost: NewPostDto): Response<BaseResponse<List<PostDto>>> = service.postFeed(newPost)
    override suspend fun getPetProfile(petId: String?, userId: String?): Response<BaseResponse<PetDto>> = service.getPetProfile(petId, userId)
    override suspend fun likePost(postId: String?): Response<BaseResponse<List<PostDto>>> = service.likePost(postId)
    override suspend fun unlikePost(postId: String?): Response<BaseResponse<List<PostDto>>> = service.unlikePost(postId)
    override suspend fun profilePost(): Response<BaseResponse<List<PostDto>>> = service.profilePost()
}