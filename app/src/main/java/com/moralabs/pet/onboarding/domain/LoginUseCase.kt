package com.moralabs.pet.onboarding.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginRequestDto
import com.moralabs.pet.onboarding.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseUseCase() {
    fun login(loginPet: LoginRequestDto): Flow<BaseResult<LoginDto>> {
        return flow {
            loginRepository.login(loginPet).body().let {
                LoginDto(
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