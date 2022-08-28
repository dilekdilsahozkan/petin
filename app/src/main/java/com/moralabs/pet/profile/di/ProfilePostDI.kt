package com.moralabs.pet.profile.di

import com.moralabs.pet.profile.data.remote.api.ProfilePostService
import com.moralabs.pet.profile.data.repository.ProfilePostRepository
import com.moralabs.pet.profile.data.repository.ProfilePostRepositoryImpl
import com.moralabs.pet.profile.domain.ProfilePostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfilePostDI {
    @Provides
    @Singleton
    fun provideProfilePostService(retrofit: Retrofit): ProfilePostService =
        retrofit.create(ProfilePostService::class.java)

    @Provides
    @Singleton
    fun provideProfilePostRepository(service: ProfilePostService): ProfilePostRepository =
        ProfilePostRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideProfilePostUseCase(profilePostRepository: ProfilePostRepository): ProfilePostUseCase =
        ProfilePostUseCase(profilePostRepository)
}