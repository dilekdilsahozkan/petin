package com.moralabs.pet.profile.di

import com.moralabs.pet.profile.data.remote.api.PetService
import com.moralabs.pet.profile.data.repository.PetRepository
import com.moralabs.pet.profile.data.repository.PetRepositoryImpl
import com.moralabs.pet.profile.domain.PetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PetDI {
    @Provides
    @Singleton
    fun providePetService(retrofit: Retrofit): PetService =
        retrofit.create(PetService::class.java)

    @Provides
    @Singleton
    fun providePetRepository(service: PetService): PetRepository =
        PetRepositoryImpl(service)

    @Provides
    @Singleton
    fun providePetUseCase(petRepository: PetRepository): PetUseCase =
        PetUseCase(petRepository)
}