package com.moralabs.pet.onboarding.di

import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.notification.data.repository.NotificationRepository
import com.moralabs.pet.onboarding.data.remote.api.LoginService
import com.moralabs.pet.onboarding.data.repository.LoginRepository
import com.moralabs.pet.onboarding.data.repository.LoginRepositoryImpl
import com.moralabs.pet.onboarding.domain.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginDI {
    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun provideLoginRepository(service: LoginService): LoginRepository =
        LoginRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideLoginUseCase(
        loginRepository: LoginRepository,
        authenticationRepository: AuthenticationRepository,
        notificationRepository: NotificationRepository
    ): LoginUseCase =
        LoginUseCase(loginRepository, authenticationRepository, notificationRepository)
}