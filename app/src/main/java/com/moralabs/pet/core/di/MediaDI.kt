package com.moralabs.pet.core.di

import com.moralabs.pet.core.data.remote.api.MediaService
import com.moralabs.pet.core.data.repository.MediaRepository
import com.moralabs.pet.core.data.repository.impl.MediaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MediaDI {
    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) = retrofit.create(MediaService::class.java)

    @Provides
    @Singleton
    fun provideRepository(service: MediaService): MediaRepository =
        MediaRepositoryImpl(service)

}