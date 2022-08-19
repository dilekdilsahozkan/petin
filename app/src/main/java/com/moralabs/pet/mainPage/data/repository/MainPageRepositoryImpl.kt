package com.moralabs.pet.mainPage.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.mainPage.data.remote.api.MainPageService
import javax.inject.Inject

class MainPageRepositoryImpl @Inject constructor(private val service: MainPageService) :
    MainPageRepository, BaseRepository {
}