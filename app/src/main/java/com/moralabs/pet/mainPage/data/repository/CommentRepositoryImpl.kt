package com.moralabs.pet.mainPage.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.mainPage.data.remote.api.CommentService
import com.moralabs.pet.mainPage.data.remote.dto.CommentRequestDto
import retrofit2.Response
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(private val service: CommentService) :
    CommentRepository, BaseRepository {
    override suspend fun writeComment(postId: String?, writeNewComment: CommentRequestDto): Response<BaseResponse<CommentDto>> = service.writeComment(postId, writeNewComment)
    override suspend fun getComment(postId: String?): Response<BaseResponse<CommentDto>> = service.getComments(postId)
}