package com.moralabs.pet.mainPage.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.data.remote.dto.PostDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface CommentService {
    @POST("/feed/post/{postId}/comment")
    suspend fun writeComment(@Body writeNewComment: CommentDto): Response<BaseResponse<List<PostDto>>>

    @GET("/feed/post/{postId}/comment")
    suspend fun commentPage(): Response<BaseResponse<List<PostDto>>>
/*
    @POST("/feed/post/comment/{commentId}/like")
    suspend fun likeComment():Response<BaseResponse<>>

    @PATCH("/feed/post/comment/{commentId}/unlike")
    suspend fun unlikeComment():Response<BaseResponse<>>*/
}