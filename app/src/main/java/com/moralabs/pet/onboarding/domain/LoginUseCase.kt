package com.moralabs.pet.onboarding.domain

import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import com.moralabs.pet.notification.data.repository.NotificationRepository
import com.moralabs.pet.notification.domain.NotificationUseCase
import com.moralabs.pet.onboarding.data.remote.dto.ForgotPasswordDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginRequestDto
import com.moralabs.pet.onboarding.data.remote.dto.NewPasswordDto
import com.moralabs.pet.onboarding.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val notificationRepository: NotificationRepository
) : BaseUseCase() {
    fun login(loginPet: LoginRequestDto): Flow<BaseResult<LoginDto>> {
        return flow {
            if (loginPet.email.isNullOrEmpty() && loginPet.password.isNullOrEmpty()) {
                emit(BaseResult.Error(ErrorResult(code = ErrorCode.EMPTY_BLANKS)))
            } else {
                val response = loginRepository.login(loginPet)
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.data?.let {
                        it.accessToken?.let { accessToken ->
                            it.refreshToken?.let { refreshToken ->
                                authenticationRepository.login(it.userId, accessToken, refreshToken)
                            }
                        }

                        notificationRepository.sendToken(notificationRepository.getFirebaseToken())

                        emit(BaseResult.Success(it))
                    } ?: run {
                        emit(BaseResult.Error(ErrorResult(code = ErrorCode.AUTH_WRONG_EMAIL_OR_PASSWORD)))
                    }
                } else {
                    emit(BaseResult.Error(ErrorResult(code = ErrorCode.SERVER_ERROR)))
                }
            }
        }
    }

    fun forgotPassword(sendEmail: ForgotPasswordDto): Flow<BaseResult<LoginDto>> {
        return flow {
            loginRepository.forgotPassword(sendEmail).body()?.data?.let {
                emit(BaseResult.Success(it))
            }
        }
    }

    fun newPassword(getCode: NewPasswordDto): Flow<BaseResult<LoginDto>> {
        return flow {
            loginRepository.newPassword(getCode).body()?.data?.let {
                emit(BaseResult.Success(it))

            }
        }
    }

}