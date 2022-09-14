package com.moralabs.pet.core.di

import com.moralabs.pet.core.data.remote.api.FeedService
import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.core.data.repository.PostRepository
import com.moralabs.pet.core.data.repository.impl.PostRepositoryImpl
import com.moralabs.pet.mainPage.domain.MainPageUseCase
import com.moralabs.pet.profile.domain.ProfilePostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FeedDI {
    @Provides
    @Singleton
    fun provideMainPageService(retrofit: Retrofit): FeedService =
        retrofit.create(FeedService::class.java)

    @Provides
    @Singleton
    fun provideMainPageRepository(service: FeedService): PostRepository =
        PostRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideMainPageUseCase(postRepository: PostRepository): MainPageUseCase =
        MainPageUseCase(postRepository)

    @Provides
    @Singleton
    fun provideProfilePostUseCase(postRepository: PostRepository, authenticationRepository: AuthenticationRepository): ProfilePostUseCase =
        ProfilePostUseCase(postRepository, authenticationRepository)
}