package com.moralabs.pet.onboarding.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import com.moralabs.pet.message.data.remote.dto.ChatDto
import com.moralabs.pet.notification.data.repository.NotificationRepository
import com.moralabs.pet.onboarding.data.remote.dto.*
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
            val response = loginRepository.login(loginPet)
            val error = Gson().fromJson(response.errorBody()?.string(), BaseResponse::class.java)
            if (loginPet.email.isNullOrEmpty() && loginPet.password.isNullOrEmpty()) {
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.EMPTY_BLANKS,
                            error.userMessage
                        )
                    )
                )
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
                        emit(
                            BaseResult.Error(
                                ErrorResult(
                                    code = ErrorCode.AUTH_WRONG_EMAIL_OR_PASSWORD,
                                    error.userMessage
                                )
                            )
                        )
                    }
                } else {
                    emit(
                        BaseResult.Error(
                            ErrorResult(
                                code = ErrorCode.SERVER_ERROR,
                                error.userMessage
                            )
                        )
                    )
                }
            }
        }
    }

    fun externalLogin(external: ExternalLoginDto): Flow<BaseResult<LoginDto>> {
        return flow {
            val external = loginRepository.externalLogin(external)
            if (external.isSuccessful && external.code() == 200) {
                emit(
                    BaseResult.Success(
                        external.body()?.data ?: LoginDto()
                    )
                )
            } else {
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            external.body()?.userMessage,
                            external.code()
                        )
                    )
                )
            }
        }
    }

    fun guestLogin(): Flow<BaseResult<LoginDto>> {
        return flow {
            val guest = loginRepository.guestLogin()
            val error = Gson().fromJson(guest.errorBody()?.string(), BaseResponse::class.java)
            if (guest.isSuccessful && guest.code() == 200) {
                guest.body()?.data?.let {
                    it.accessToken?.let { accessToken ->
                        authenticationRepository.guestLogin(accessToken)
                    }
                    emit(BaseResult.Success(it))
                }
            } else {
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun forgotPassword(sendEmail: ForgotPasswordDto): Flow<BaseResult<Boolean>> {
        return flow {
            val forgot = loginRepository.forgotPassword(sendEmail)
            val error = Gson().fromJson(forgot.errorBody()?.string(), BaseResponse::class.java)
            if (sendEmail.email.isNullOrEmpty()) {
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.EMPTY_BLANKS,
                            error.userMessage
                        )
                    )
                )
            } else {
                if (forgot.isSuccessful && forgot.code() == 200) {
                    emit(BaseResult.Success(true))
                } else {
                    emit(
                        BaseResult.Error(
                            ErrorResult(
                                code = ErrorCode.SERVER_ERROR,
                                error.userMessage
                            )
                        )
                    )
                }
            }
        }
    }

    fun passwordCode(passwordCode: PasswordCodeDto): Flow<BaseResult<Any>> {
        return flow {
            val pwCode = loginRepository.passwordCode(passwordCode)
            val error = Gson().fromJson(pwCode.errorBody()?.string(), BaseResponse::class.java)
            if (pwCode.isSuccessful && pwCode.code() == 200) {
                emit(BaseResult.Success(true))
            } else {
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun newPassword(newPw: NewPasswordDto): Flow<BaseResult<Boolean>> {
        return flow {
            val response = loginRepository.newPassword(newPw)
            if (response.isSuccessful && response.code() == 200) {
                emit(BaseResult.Success(response.isSuccessful))
            } else {
                val error =
                    Gson().fromJson(response.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }
}