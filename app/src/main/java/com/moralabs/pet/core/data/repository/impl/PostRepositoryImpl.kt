package com.moralabs.pet.core.data.repository.impl

import com.moralabs.pet.core.data.remote.api.FeedService
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.core.data.repository.PostRepository
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import retrofit2.Response
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val service: FeedService) :
    PostRepository, BaseRepository {
    override suspend fun getFeed(searchQuery: String?, index: Int?, size: Int?, dateTime: Long?) = service.getFeed(searchQuery, index, size, dateTime)
    override suspend fun postFeed(newPost: NewPostDto) = service.postFeed(newPost)
    override suspend fun getPetProfile(petId: String?, userId: String?) = service.getPetProfile(petId, userId)
    override suspend fun likePost(postId: String?) = service.likePost(postId)
    override suspend fun unlikePost(postId: String?) = service.unlikePost(postId)
    override suspend fun reportPost(postId: String?, reportType: Int?) = service.reportPost(postId, reportType)
    override suspend fun profilePost() = service.profilePost()
    override suspend fun getPostAnotherUser(userId: String?) = service.getPostAnotherUser(userId)
    override suspend fun deletePost(postId: String?): Response<BaseResponse<Nothing>> = service.deletePost(postId)
}