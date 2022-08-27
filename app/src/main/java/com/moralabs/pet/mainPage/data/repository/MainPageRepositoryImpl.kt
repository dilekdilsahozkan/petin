package com.moralabs.pet.mainPage.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.mainPage.data.remote.api.PostService
import javax.inject.Inject

class MainPageRepositoryImpl @Inject constructor(private val service: PostService) :
    MainPageRepository, BaseRepository {
}