package com.moralabs.pet.core.di

import com.moralabs.pet.BuildConfig
import com.moralabs.pet.MainActivity
import com.moralabs.pet.core.data.remote.interceptor.AuthenticationInterceptorRefreshToken
import com.moralabs.pet.core.data.remote.interceptor.HeaderInterceptor
import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.core.data.repository.impl.AuthenticationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkDI {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        authenticationInterceptorRefreshToken: AuthenticationInterceptorRefreshToken
    ) = if (true) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(authenticationInterceptorRefreshToken)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .addInterceptor(headerInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.PET_BASEURL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideHeaderInterceptor(authenticationRepository: AuthenticationRepository): HeaderInterceptor =
        HeaderInterceptor(authenticationRepository)

    @Provides
    @Singleton
    fun proviceAuthenticationInterceptorRefreshToken(
        authenticationRepository: AuthenticationRepository
    ) =
        AuthenticationInterceptorRefreshToken(authenticationRepository)

    @Provides
    @Singleton
    fun provideAuthenticationRepository(): AuthenticationRepository =
        AuthenticationRepositoryImpl(MainActivity.instance)
}