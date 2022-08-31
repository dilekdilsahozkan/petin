package com.moralabs.pet.newPost.di

import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.newPost.data.remote.api.NewPostService
import com.moralabs.pet.newPost.data.repository.NewPostRepository
import com.moralabs.pet.newPost.data.repository.NewPostRepositoryImpl
import com.moralabs.pet.newPost.domain.NewPostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewPostDI {
    @Provides
    @Singleton
    fun provideNewPostService(retrofit: Retrofit): NewPostService =
        retrofit.create(NewPostService::class.java)

    @Provides
    @Singleton
    fun provideNewPostRepository(service: NewPostService): NewPostRepository =
        NewPostRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideNewPostUseCase(
        newPostRepository: NewPostRepository
    ): NewPostUseCase =
        NewPostUseCase(newPostRepository)
}