package com.moralabs.pet.message.di

import com.moralabs.pet.message.data.remote.api.MessageService
import com.moralabs.pet.message.data.repository.MessageRepository
import com.moralabs.pet.message.data.repository.MessageRepositoryImpl
import com.moralabs.pet.message.domain.MessageUseCase
import com.moralabs.pet.profile.data.remote.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MessageDI {
    @Provides
    @Singleton
    fun provideMessageService(retrofit: Retrofit): MessageService =
        retrofit.create(MessageService::class.java)

    @Provides
    @Singleton
    fun provideMessageRepository(service: MessageService, userService: UserService): MessageRepository =
        MessageRepositoryImpl(service, userService)

    @Provides
    @Singleton
    fun provideMessageUseCase(
        messageRepository: MessageRepository
    ): MessageUseCase =
        MessageUseCase(messageRepository)
}