package com.moralabs.pet.mainPage.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.mainPage.data.remote.api.CommentService
import com.moralabs.pet.mainPage.data.remote.dto.CommentRequestDto
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(private val service: CommentService) :
    CommentRepository, BaseRepository {
    override suspend fun writeComment(postId: String?, writeNewComment: CommentRequestDto) = service.writeComment(postId, writeNewComment)
    override suspend fun getComment(postId: String?) = service.getComments(postId)
    override suspend fun deleteComment(commentId: String?) = service.deletePost(commentId)
}