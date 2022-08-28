package com.moralabs.pet.profile.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.profile.data.remote.api.ProfilePostService
import retrofit2.Response
import javax.inject.Inject

class ProfilePostRepositoryImpl @Inject constructor(private val service: ProfilePostService) :
    ProfilePostRepository, BaseRepository {
    override suspend fun profilePost(): Response<BaseResponse<List<PostDto>>> = service.getPost()
}