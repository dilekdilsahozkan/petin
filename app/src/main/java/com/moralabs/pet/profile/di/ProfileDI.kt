package com.moralabs.pet.profile.di

import com.moralabs.pet.profile.data.remote.api.UserService
import com.moralabs.pet.profile.data.repository.ProfileRepository
import com.moralabs.pet.profile.data.repository.ProfileRepositoryImpl
import com.moralabs.pet.profile.domain.ProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileDI {
    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideProfileRepository(service: UserService): ProfileRepository =
        ProfileRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideProfileUseCase(profileRepository: ProfileRepository): ProfileUseCase =
        ProfileUseCase(profileRepository)
}