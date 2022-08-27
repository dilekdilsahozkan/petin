package com.moralabs.pet.core.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface FeedService {

    @GET("/feed")
    suspend fun getFeed(): Response<BaseResponse<List<PostDto>>>

    @POST("/feed/post")
    suspend fun postPet(
        @Body newPost: NewPostDto
    ): Response<BaseResponse<List<PostDto>>>

    @GET("/feed/post")
    suspend fun getPost(): Response<BaseResponse<List<PostDto>>>

    @POST("/feed/post/{postId}/{dateTime}")
    suspend fun postDateTime(): Response<BaseResponse<PostDto>>

    @GET("/feed/post/user/{userId}")
    suspend fun getPostAnotherUser(): Response<BaseResponse<PostDto>>

    @GET("/feed/liked")
    suspend fun getLiked(): Response<BaseResponse<PostDto>>

    @POST("/feed/post/{postId}/like")
    suspend fun likePost(): Response<BaseResponse<*>>

    @PATCH("/feed/post/{postId}/unlike")
    suspend fun unlikePost(): Response<BaseResponse<*>>

    @PATCH("/feed/post/{postId}")
    suspend fun deletePost(): Response<BaseResponse<*>>
}