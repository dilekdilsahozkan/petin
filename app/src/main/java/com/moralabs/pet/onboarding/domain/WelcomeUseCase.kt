package com.moralabs.pet.onboarding.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.onboarding.data.remote.dto.WelcomeDto
import com.moralabs.pet.onboarding.data.repository.WelcomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WelcomeUseCase @Inject constructor(
    private val welcomeRepository: WelcomeRepository
) : BaseUseCase() {

    fun getWelcomePage(): Flow<BaseResult<WelcomeDto>> {
        return flow {
            var welcome = welcomeRepository.getWelcomePage().body().let {
                WelcomeDto(
                    testVariable = it?.data?.testVariable
                )
            }
            emit(
                BaseResult.Success(
                    welcome
                )
            )
        }
    }
}