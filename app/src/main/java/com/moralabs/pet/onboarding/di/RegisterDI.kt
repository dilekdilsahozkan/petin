package com.moralabs.pet.onboarding.di

import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.notification.data.repository.NotificationRepository
import com.moralabs.pet.notification.domain.NotificationUseCase
import com.moralabs.pet.onboarding.data.remote.api.RegisterService
import com.moralabs.pet.onboarding.data.repository.RegisterRepository
import com.moralabs.pet.onboarding.data.repository.RegisterRepositoryImpl
import com.moralabs.pet.onboarding.domain.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RegisterDI {
    @Provides
    @Singleton
    fun provideRegisterService(retrofit: Retrofit): RegisterService =
        retrofit.create(RegisterService::class.java)

    @Provides
    @Singleton
    fun provideRegisterRepository(service: RegisterService): RegisterRepository =
        RegisterRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        loginRepository: RegisterRepository,
        authenticationRepository: AuthenticationRepository,
        notificationRepository: NotificationRepository
    ): RegisterUseCase =
        RegisterUseCase(loginRepository, authenticationRepository, notificationRepository)
}