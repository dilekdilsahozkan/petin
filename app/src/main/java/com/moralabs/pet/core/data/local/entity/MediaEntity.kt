package com.moralabs.pet.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Media",
    foreignKeys = [
        ForeignKey(entity = PostEntity::class,
            parentColumns = ["id"],
            childColumns = ["postId"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class MediaEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val postId: String,
    val url: String? = null
)