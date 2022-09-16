package com.moralabs.pet.newPost.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.newPost.data.remote.api.NewPostService
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import javax.inject.Inject

class NewPostRepositoryImpl @Inject constructor(private val service: NewPostService)
    : NewPostRepository, BaseRepository {
    override suspend fun createPost(createNewPost: NewPostDto) = service.createPost(createNewPost)
    override suspend fun getLocation() = service.getLocation()
}