package com.moralabs.pet.notification.domain

import android.os.Debug
import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import com.moralabs.pet.notification.data.repository.NotificationRepository
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
            var notification = notificationRepository.notificationPet().body()?.data ?: listOf()
            emit(
                BaseResult.Success(
                    notification
                )
            )
        }
    }

    fun notificationDateTime(dateTime: String?): Flow<BaseResult<List<NotificationDto>>> {
        return flow {
            var notificationTime = notificationRepository.notificationDateTime(dateTime).body()?.data ?: listOf()
            emit(
                BaseResult.Success(
                    notificationTime
                )
            )
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