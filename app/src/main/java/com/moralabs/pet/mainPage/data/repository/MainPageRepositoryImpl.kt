package com.moralabs.pet.mainPage.data.repository

import com.moralabs.pet.core.data.remote.api.FeedService
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.mainPage.data.remote.api.PostService
import retrofit2.Response
import javax.inject.Inject

class MainPageRepositoryImpl @Inject constructor(private val service: FeedService) :
    MainPageRepository, BaseRepository {
    override suspend fun getFeed(): Response<BaseResponse<PostDto>> = service.getFeed()

}