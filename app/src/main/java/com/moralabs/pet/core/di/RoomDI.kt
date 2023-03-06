package com.moralabs.pet.core.di

import android.content.Context
import androidx.room.Room
import com.moralabs.pet.core.data.local.db.PetDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDI {

    @Singleton
    @Provides
    fun providePetDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        PetDb::class.java,
        "pet_db"
    ).build()

    @Singleton
    @Provides
    fun providePostDao(db: PetDb) = db.postDao()
}