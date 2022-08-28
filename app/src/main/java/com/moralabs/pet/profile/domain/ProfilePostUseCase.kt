package com.moralabs.pet.profile.domain

import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.profile.data.repository.ProfilePostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfilePostUseCase @Inject constructor(
    private val profilePostRepository: ProfilePostRepository
) : BaseUseCase() {

    fun profilePost(): Flow<BaseResult<List<PostDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    profilePostRepository.profilePost().body()?.data ?: listOf()
                )
            )
        }

    }
}