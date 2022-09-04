package com.moralabs.pet.mainPage.di

import com.moralabs.pet.mainPage.data.remote.api.CommentService
import com.moralabs.pet.mainPage.data.repository.CommentRepository
import com.moralabs.pet.mainPage.data.repository.CommentRepositoryImpl
import com.moralabs.pet.mainPage.domain.CommentUseCase
import com.moralabs.pet.profile.data.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommentDI {
    @Provides
    @Singleton
    fun provideCommentService(retrofit: Retrofit): CommentService =
        retrofit.create(CommentService::class.java)

    @Provides
    @Singleton
    fun provideCommentRepository(service: CommentService): CommentRepository =
        CommentRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideCommentUseCase(commentRepository: CommentRepository, userCommentRepository: CommentRepository): CommentUseCase =
        CommentUseCase(commentRepository, userCommentRepository)
}