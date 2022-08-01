package com.moralabs.pet.onboarding.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.onboarding.data.remote.dto.RegisterDto
import com.moralabs.pet.onboarding.data.remote.dto.RegisterRequestDto
import com.moralabs.pet.onboarding.data.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase  @Inject constructor(
    private val registerRepository: RegisterRepository
) : BaseUseCase() {
    fun register(registerPet: RegisterRequestDto): Flow<BaseResult<RegisterDto>> {
        return flow {
            registerRepository.register(registerPet).body().let {
                RegisterDto(
                    accessToken = it?.data?.accessToken,
                    refreshToken = it?.data?.refreshToken,
                    accessTokenExpiration = it?.data?.accessTokenExpiration,
                    refreshTokenExpiration = it?.data?.refreshTokenExpiration,
                    tokenType = it?.data?.tokenType

                )
            }
        }
    }
}