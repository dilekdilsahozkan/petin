package com.moralabs.pet.mainPage.di

import com.moralabs.pet.mainPage.data.remote.api.PostService
import com.moralabs.pet.mainPage.data.repository.MainPageRepository
import com.moralabs.pet.mainPage.data.repository.MainPageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainPageDI {
    @Provides
    @Singleton
    fun provideMainPageService(retrofit: Retrofit): PostService =
        retrofit.create(PostService::class.java)

    @Provides
    @Singleton
    fun provideMainPageRepository(service: PostService): MainPageRepository =
        MainPageRepositoryImpl(service)

    /* @Provides
     @Singleton
     fun provideMainPageUseCase(mainPageRepository: MainPageRepository): MainPageUseCase =
         MainPageUseCase(mainPageRepository)*/
}