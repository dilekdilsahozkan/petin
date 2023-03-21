package com.moralabs.pet.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "PetAttribute",
    foreignKeys = [
        ForeignKey(entity = PostEntity::class,
            parentColumns = ["id"],
            childColumns = ["postId"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class PetAttributeEntity (
    val attributeType: Int? = null,
    val choice: String? = null,
    val postId: String
)