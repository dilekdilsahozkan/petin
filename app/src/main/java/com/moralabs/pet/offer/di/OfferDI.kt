package com.moralabs.pet.offer.di

import com.moralabs.pet.offer.data.remote.api.OfferService
import com.moralabs.pet.offer.data.repository.OfferRepository
import com.moralabs.pet.offer.data.repository.OfferRepositoryImpl
import com.moralabs.pet.offer.domain.OfferUseCase
import com.moralabs.pet.petProfile.data.repository.PetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OfferDI {
    @Provides
    @Singleton
    fun provideOfferService(retrofit: Retrofit): OfferService =
        retrofit.create(OfferService::class.java)

    @Provides
    @Singleton
    fun provideOfferRepository(service: OfferService): OfferRepository =
        OfferRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideOfferUseCase(
        offerRepository: OfferRepository, petRepository: PetRepository
    ): OfferUseCase =
        OfferUseCase(offerRepository, petRepository)
}