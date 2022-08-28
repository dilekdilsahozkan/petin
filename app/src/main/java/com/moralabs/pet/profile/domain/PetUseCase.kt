package com.moralabs.pet.profile.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.profile.data.remote.dto.PetDto
import com.moralabs.pet.profile.data.repository.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PetUseCase @Inject constructor(
    private val petRepository: PetRepository
) : BaseUseCase() {

    fun petPost(): Flow<BaseResult<List<PetDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    petRepository.petPost().body()?.data ?: listOf()
                )
            )
        }

    }
}