package com.moralabs.pet.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Post")
data class PostEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val dateTime: Long? = null,
    val userId: String? = null,
    val userName: String? = null,
    val userImage: String? = null,
    val contentText: String? = null,
    var likeCount: Int? = null,
    val commentCount: Int? = null,
    val offerCount: Int? = null,
    val isOfferAvailableByUser: Boolean? = null,
    var isPostLikedByUser: Boolean? = null,
    val isPostOwnedByUser: Boolean? = null,
    val petName: String? = null,
    val petMediaUrl: String? = null,
    val petAttributes: List<PetAttributeEntity?>? = null,
    val locationCity: String? = null,
    val contentMediaEntity: List<MediaEntity>? = null,
    val contentType: Int? = null,
)