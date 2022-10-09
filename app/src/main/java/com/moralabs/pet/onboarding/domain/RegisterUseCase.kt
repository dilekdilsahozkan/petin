package com.moralabs.pet.onboarding.domain

import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import com.moralabs.pet.notification.data.repository.NotificationRepository
import com.moralabs.pet.onboarding.data.remote.dto.RegisterDto
import com.moralabs.pet.onboarding.data.remote.dto.RegisterRequestDto
import com.moralabs.pet.onboarding.data.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val notificationRepository: NotificationRepository
) : BaseUseCase() {

    fun register(registerPet: RegisterRequestDto): Flow<BaseResult<RegisterDto>> {
        return flow {
            if (registerPet.fullName.isNullOrEmpty() && registerPet.username.isNullOrEmpty() && registerPet.email.isNullOrEmpty() && registerPet.password.isNullOrEmpty()) {
                emit(BaseResult.Error(ErrorResult(code = ErrorCode.EMPTY_BLANKS)))
            } else if (registerPet.password.toString().length < 8) {
                emit(BaseResult.Error(ErrorResult(code = ErrorCode.PASSWORD_LENGTH_LESS_THAN_EIGHT)))
            } else {
                val result = registerRepository.register(registerPet)

                if (result.isSuccessful && result.code() == 200) {
                    result.body()?.data?.let {
                        it.accessToken?.let { accessToken ->
                            it.refreshToken?.let { refreshToken ->
                                authenticationRepository.login(it.userId, accessToken, refreshToken)
                            }
                        }

                        notificationRepository.sendToken(notificationRepository.getFirebaseToken())

                        emit(BaseResult.Success(it))
                    } ?: run {
                        emit(BaseResult.Error(ErrorResult(code = ErrorCode.NO_DATA)))
                    }
                } else if (result.code() == 111) {
                    emit(
                        BaseResult.Error(
                            ErrorResult(
                                code = ErrorCode.USERNAME_VALID
                            )
                        )
                    )
                } else if (result.code() == 112) {
                    emit(
                        BaseResult.Error(
                            ErrorResult(
                                code = ErrorCode.EMAIL_VALID
                            )
                        )
                    )
                } else {
                    emit(BaseResult.Error(ErrorResult(code = ErrorCode.SERVER_ERROR)))
                }
            }
        }
    }
}
