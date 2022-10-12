package com.moralabs.pet.notification.data.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.notification.data.remote.api.NotificationService
import com.moralabs.pet.notification.data.remote.dto.NotificationTokenDto
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class NotificationRepositoryImpl @Inject constructor(private val service: NotificationService) :
    NotificationRepository, BaseRepository {
    override suspend fun notificationPet() = service.notificationPet()
    override suspend fun latestNotification() = service.latestNotification()
    override suspend fun notificationDateTime(dateTime: String?) = service.notificationDateTime(dateTime)
    override suspend fun sendToken(token: String?) = service.sendToken(NotificationTokenDto(token))
    override suspend fun getFirebaseToken() = suspendCoroutine<String?> { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->

            if (!task.isSuccessful) {
                continuation.resumeWith(Result.success(null))
                return@OnCompleteListener
            }
            continuation.resumeWith(Result.success(task.result))
        })
    }
}