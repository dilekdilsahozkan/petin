package com.moralabs.pet.mainPage.domain

import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.mainPage.data.remote.dto.CommentRequestDto
import com.moralabs.pet.mainPage.data.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) : BaseUseCase() {

    fun writeComment(
        postId: String?,
        writeNewComment: CommentRequestDto
    ): Flow<BaseResult<CommentDto>> {
        return flow {
                commentRepository.writeComment(postId, writeNewComment).body()?.data?.let {
                    emit(BaseResult.Success(it))
                }

        }
    }

    fun getComments(postId: String?): Flow<BaseResult<CommentDto>> {
        return flow {
             commentRepository.getComment(postId).body()?.data?.let {
                 emit(
                     BaseResult.Success(it)
                 )
             }

        }
    }
}
