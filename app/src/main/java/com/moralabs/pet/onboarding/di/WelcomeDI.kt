package com.moralabs.pet.onboarding.di

import com.moralabs.pet.onboarding.data.remote.api.WelcomeService
import com.moralabs.pet.onboarding.data.repository.WelcomeRepository
import com.moralabs.pet.onboarding.data.repository.WelcomeRepositoryImpl
import com.moralabs.pet.onboarding.domain.WelcomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WelcomeDI {
    @Provides
    @Singleton
    fun provideWelcomeService(retrofit: Retrofit): WelcomeService = retrofit.create(WelcomeService::class.java)

    @Provides
    @Singleton
    fun provideWelcomeRepository(service: WelcomeService): WelcomeRepository = WelcomeRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideWelcomeUseCase(welcomeRepository: WelcomeRepository): WelcomeUseCase =
        WelcomeUseCase(welcomeRepository)
}