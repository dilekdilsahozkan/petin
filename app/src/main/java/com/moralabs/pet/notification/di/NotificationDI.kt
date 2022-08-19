package com.moralabs.pet.notification.di

import com.moralabs.pet.notification.data.remote.api.NotificationService
import com.moralabs.pet.notification.data.repository.NotificationRepository
import com.moralabs.pet.notification.data.repository.NotificationRepositoryImpl
import com.moralabs.pet.notification.domain.NotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotificationDI {
    @Provides
    @Singleton
    fun provideNotificationService(retrofit: Retrofit): NotificationService =
        retrofit.create(NotificationService::class.java)

    @Provides
    @Singleton
    fun provideNotificationRepository(service: NotificationService): NotificationRepository =
        NotificationRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideNotificationUseCase(notificationRepository: NotificationRepository): NotificationUseCase =
        NotificationUseCase(notificationRepository)
}