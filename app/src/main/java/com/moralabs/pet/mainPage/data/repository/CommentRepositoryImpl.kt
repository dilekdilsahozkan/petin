package com.moralabs.pet.mainPage.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.mainPage.data.remote.api.CommentService
import retrofit2.Response
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(private val service: CommentService) :
    CommentRepository, BaseRepository {
    override suspend fun writeComment(writeNewComment: CommentDto): Response<BaseResponse<List<PostDto>>> = service.writeComment(writeNewComment)

    override suspend fun commentPage(): Response<BaseResponse<List<PostDto>>> = service.commentPage()
}