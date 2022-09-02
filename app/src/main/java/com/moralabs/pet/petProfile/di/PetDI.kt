package com.moralabs.pet.petProfile.di

import com.moralabs.pet.petProfile.data.remote.api.PetService
import com.moralabs.pet.petProfile.data.repository.PetRepository
import com.moralabs.pet.petProfile.data.repository.PetRepositoryImpl
import com.moralabs.pet.petProfile.domain.PetUseCase
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
    fun petRepositoryprovidePetRepository(service: PetService): PetRepository =
        PetRepositoryImpl(service)

    @Provides
    @Singleton
    fun providePetUseCase(petRepository: PetRepository): PetUseCase =
        PetUseCase(petRepository)
}