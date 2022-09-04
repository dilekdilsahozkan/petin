package com.moralabs.pet.mainPage.domain

import com.moralabs.pet.core.data.remote.dto.CreateCommentDto
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.mainPage.data.remote.dto.CommentRequestDto
import com.moralabs.pet.mainPage.data.repository.CommentRepository
import com.moralabs.pet.mainPage.data.repository.MainPageRepository
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.repository.ProfilePostRepository
import com.moralabs.pet.profile.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository,
    private val userCommentRepository: CommentRepository
) : BaseUseCase() {


    fun writeComment(postId: String?, writeNewComment: CommentRequestDto): Flow<BaseResult<CreateCommentDto>> {
        return flow {
            val commentValue =
                commentRepository.writeComment(postId, writeNewComment).body()?.data ?: listOf()
            emit(
                BaseResult.Success(
                    CreateCommentDto(
                        commentValue = commentValue
                    )
                )
            )
        }
    }

    fun getComments(postId: String?): Flow<BaseResult<CreateCommentDto>> {
        return flow {

            val userValue = userCommentRepository?.getComment(postId)?.body()?.data
            emit(
                BaseResult.Success(
                    CreateCommentDto(
                        userCommentValue = userValue
                    )
                )
            )
        }
    }
}
