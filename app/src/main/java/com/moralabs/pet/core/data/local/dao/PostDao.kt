package com.moralabs.pet.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moralabs.pet.core.data.local.entity.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM Post ORDER BY dateTime DESC LIMIT 100")
    suspend fun getAllPosts(): List<PostEntity>

    @Query("SELECT * FROM Post WHERE dateTime > :dateTime ORDER BY dateTime DESC LIMIT 100")
    suspend fun getAllPostsFrom(dateTime: Long): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPosts(postEntities: List<PostEntity>)

    @Query("DELETE FROM Post WHERE id = :id")
    suspend fun deletePost(id: String)

    @Query("UPDATE Post SET isPostLikedByUser = :liked WHERE id = :id")
    suspend fun setIsMyLike(id: String, liked: Boolean)

    @Query("UPDATE Post SET likeCount = likeCount + 1 WHERE id = :id")
    suspend fun incrementLikeCount(id: String)

    @Query("UPDATE Post SET likeCount = likeCount - 1 WHERE id = :id")
    suspend fun descrementLikeCount(id: String)
}