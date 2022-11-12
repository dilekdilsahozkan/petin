package com.moralabs.pet.core.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moralabs.pet.core.data.local.entity.MediaEntity
import com.moralabs.pet.core.data.local.entity.PetAttributeEntity
import java.lang.reflect.Type

object PetTypeConverters {
    @TypeConverter
    fun fromStringToPetAttributeList(value: String?): List<PetAttributeEntity>? {
        if (value.isNullOrEmpty()) return null

        val listType: Type = object : TypeToken<List<PetAttributeEntity?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromPetAttributeListToString(list: List<PetAttributeEntity?>?): String? {
        if (list.isNullOrEmpty()) return null

        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromListToMedia(value: String?): List<MediaEntity>? {
        if (value.isNullOrEmpty()) return null

        val listType: Type = object : TypeToken<List<MediaEntity?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromMediaListToString(list: List<MediaEntity>?): String? {
        if (list.isNullOrEmpty()) return null

        val gson = Gson()
        return gson.toJson(list)
    }

}