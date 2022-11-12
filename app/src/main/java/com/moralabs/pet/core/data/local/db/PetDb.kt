package com.moralabs.pet.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moralabs.pet.core.data.local.converter.PetTypeConverters
import com.moralabs.pet.core.data.local.dao.PostDao
import com.moralabs.pet.core.data.local.entity.PostEntity

@Database(entities = [PostEntity::class], version = 1)
@TypeConverters(PetTypeConverters::class)
abstract class PetDb : RoomDatabase() {
    abstract fun postDao(): PostDao
}