package com.moralabs.pet.onboarding.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
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

            if (registerPet.email.isNullOrEmpty()) {
                emit(BaseResult.Error(ErrorResult(code = ErrorCode.EMPTY_EMAIL)))
            } else {
                val result = registerRepository.register(registerPet)

                if (result.isSuccessful && result.code() == 200) {
                    result.body()?.data?.run {
                        emit(BaseResult.Success(this))
                    } ?: run {
                        emit(BaseResult.Error(ErrorResult(code = ErrorCode.NO_DATA)))
                    }
                } else {
                    emit(BaseResult.Error(ErrorResult(code = ErrorCode.SERVER_ERROR)))
                }
            }
        }
    }
}
