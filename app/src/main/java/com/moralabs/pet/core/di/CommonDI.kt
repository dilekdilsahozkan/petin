package com.moralabs.pet.core.di

import com.moralabs.pet.core.presentation.observable.NotificationHandler
import com.moralabs.pet.notification.domain.NotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonDI {
    @Provides
    @Singleton
    fun provideNotificationCounter(useCase: NotificationUseCase) = NotificationHandler(useCase)
}