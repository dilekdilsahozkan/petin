package com.moralabs.pet.mainPage.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.mainPage.data.remote.dto.CommentRequestDto
import retrofit2.Response
import retrofit2.http.*

interface CommentService {

    @GET("/feed/post/{postId}")
    suspend fun getComments(
        @Path("postId") postId: String?
    ): Response<BaseResponse<CommentDto>>

    @POST("/feed/post/{postId}/comment")
    suspend fun writeComment(
        @Path("postId") postId: String?,
        @Body writeNewComment: CommentRequestDto
    ): Response<BaseResponse<CommentDto>>

    @DELETE("/feed/comment/{commentId}")
    suspend fun deletePost(@Path("commentId") commentId: String?): Response<BaseResponse<Nothing>>
}