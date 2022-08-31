package com.moralabs.pet.mainPage.domain

import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.mainPage.data.repository.MainPageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainPageUseCase @Inject constructor(
    private val mainPageRepository: MainPageRepository
) : BaseUseCase() {

    fun getFeed(): Flow<BaseResult<List<PostDto>>> {
        return flow {

            emit(
                BaseResult.Success(
                    mainPageRepository.getFeed().body()?.data ?: listOf()
                )
            )
        }
    }
}