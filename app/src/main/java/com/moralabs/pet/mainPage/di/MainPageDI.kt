package com.moralabs.pet.mainPage.di

import com.moralabs.pet.mainPage.data.remote.api.MainPageService
import com.moralabs.pet.mainPage.data.repository.MainPageRepository
import com.moralabs.pet.mainPage.data.repository.MainPageRepositoryImpl
import com.moralabs.pet.mainPage.domain.MainPageUseCase
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
    fun provideMainPageService(retrofit: Retrofit): MainPageService =
        retrofit.create(MainPageService::class.java)

    @Provides
    @Singleton
    fun provideMainPageRepository(service: MainPageService): MainPageRepository =
        MainPageRepositoryImpl(service)

    /* @Provides
     @Singleton
     fun provideMainPageUseCase(mainPageRepository: MainPageRepository): MainPageUseCase =
         MainPageUseCase(mainPageRepository)*/
}