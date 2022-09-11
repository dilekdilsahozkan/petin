package com.moralabs.pet.settings

import com.moralabs.pet.settings.data.remote.api.SettingService
import com.moralabs.pet.settings.data.repository.SettingRepository
import com.moralabs.pet.settings.data.repository.SettingRepositoryImpl
import com.moralabs.pet.settings.domain.SettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingDI {
    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): SettingService =
        retrofit.create(SettingService::class.java)

    @Provides
    @Singleton
    fun provideProfileRepository(service: SettingService): SettingRepository =
        SettingRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideProfileUseCase(settingRepository: SettingRepository): SettingsUseCase =
        SettingsUseCase(settingRepository)
}