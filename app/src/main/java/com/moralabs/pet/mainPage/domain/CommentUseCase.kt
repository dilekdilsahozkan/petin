package com.moralabs.pet.mainPage.domain

import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.mainPage.data.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) : BaseUseCase() {

    fun writeComment(writeNewComment: CommentDto): Flow<BaseResult<List<PostDto>>> {
        return flow {

            emit(
                BaseResult.Success(
                    commentRepository.writeComment(writeNewComment).body()?.data ?: listOf()
                )
            )
        }
    }

    fun commentPage(): Flow<BaseResult<List<PostDto>>> {
        return flow {

            emit(
                BaseResult.Success(
                    commentRepository.commentPage().body()?.data ?: listOf()
                )
            )
        }
    }
}