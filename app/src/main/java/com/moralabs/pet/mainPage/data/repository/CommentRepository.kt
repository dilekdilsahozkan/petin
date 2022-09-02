package com.moralabs.pet.mainPage.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.data.remote.dto.PostDto
import retrofit2.Response

interface CommentRepository {
    suspend fun writeComment(writeNewComment: CommentDto): Response<BaseResponse<List<PostDto>>>
    suspend fun commentPage(): Response<BaseResponse<List<PostDto>>>
    //  suspend fun likeComment(): Response<BaseResponse>
   //  suspend fun unlikeComment(): Response<BaseResponse>
}