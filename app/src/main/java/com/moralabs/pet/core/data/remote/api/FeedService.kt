package com.moralabs.pet.core.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response
import retrofit2.http.*

interface FeedService {

    // GET all feeds
    @GET("/feed")
    suspend fun getFeed(): Response<BaseResponse<List<PostDto>>>

    // POST new feeds
    @POST("/feed/post")
    suspend fun postFeed(
        @Body newPost: NewPostDto
    ): Response<BaseResponse<List<PostDto>>>

    // GET posts of a user
    @GET("/feed/post")
    suspend fun profilePost(): Response<BaseResponse<List<PostDto>>>

    // gönderi beğenme
    @POST("/feed/post/{postId}/like")
    suspend fun likePost(@Path("postId") postId: String?): Response<BaseResponse<List<PostDto>>>

    // gönderi beğenmesini geri çekme
    @PATCH("/feed/post/{postId}/unlike")
    suspend fun unlikePost(@Path("postId") postId: String?): Response<BaseResponse<List<PostDto>>>

    @GET("/feed/post/user/{userId}")
    suspend fun getPostAnotherUser(@Path("userId") userId: String?): Response<BaseResponse<List<PostDto>>>

    @GET("/pet/{petId}/user/{userId}")
    suspend fun getPetProfile(@Path("petId") petId: String?, @Path("userId") userId: String?): Response<BaseResponse<PetDto>>

    // beğenilen gönderiler
    @GET("/feed/liked")
    suspend fun getLiked(): Response<BaseResponse<PostDto>>

    @DELETE("/feed/post/{postId}")
    suspend fun deletePost(): Response<BaseResponse<Nothing>>
}