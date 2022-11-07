package com.moralabs.pet.notification.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import com.moralabs.pet.notification.data.repository.NotificationRepository
import com.moralabs.pet.petProfile.data.remote.dto.CreateOfferDto
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val authenticationRepository: AuthenticationRepository
) : BaseUseCase() {

    private var tokenSent = false
    private var sendTokenUser: String? = null

    fun notificationPet(): Flow<BaseResult<List<NotificationDto>>> {
        return flow {
            var notification = notificationRepository.notificationPet()
            if (notification.isSuccessful && notification.code() == 200) {
                emit(
                    BaseResult.Success(
                        notification.body()?.data ?: listOf()
                    )
                )
            } else {
                val error = Gson().fromJson(notification.errorBody()?.string(), BaseResponse::class.java)
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

    fun notificationDateTime(dateTime: String?): Flow<BaseResult<List<NotificationDto>>> {
        return flow {
            var notificationTime = notificationRepository.notificationDateTime(dateTime)
            if (notificationTime.isSuccessful && notificationTime.code() == 200) {
                emit(
                    BaseResult.Success(
                        notificationTime.body()?.data ?: listOf()
                    )
                )
            } else {
                val error = Gson().fromJson(notificationTime.errorBody()?.string(), BaseResponse::class.java)
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

    fun latestNotification(): Flow<BaseResult<Boolean>> {
        return flow {
            val notificationTime = notificationRepository.latestNotification()
            if (notificationTime.isSuccessful && notificationTime.code() == 200) {
                emit(BaseResult.Success(notificationTime.body()?.data == true))
            } else {
                val error = Gson().fromJson(notificationTime.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            message = error?.userMessage
                        )
                    )
                )
            }
        }
    }

    fun sendNotificationToken(): Flow<BaseResult<Boolean>> {
        return flow {
            if (authenticationRepository.isLoggedIn() && (tokenSent.not() || sendTokenUser != authenticationRepository.getAuthentication()?.userId)) {
                tokenSent = true
                sendTokenUser = authenticationRepository.getAuthentication()?.userId
                notificationRepository.sendToken(notificationRepository.getFirebaseToken())
            }
            emit(BaseResult.Success(true))
        }
    }
}