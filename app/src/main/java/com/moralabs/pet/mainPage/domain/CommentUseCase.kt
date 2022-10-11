package com.moralabs.pet.mainPage.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
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
            val write = commentRepository.writeComment(postId, writeNewComment)
            if(write.isSuccessful && write.code() == 200){
                write.body()?.data?.let {
                    emit(BaseResult.Success(it))
                }
            }else{
                val error = Gson().fromJson(write.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun getComments(postId: String?): Flow<BaseResult<CommentDto>> {
        return flow {
            val getComment = commentRepository.getComment(postId)
            if(getComment.isSuccessful && getComment.code() == 200){
                getComment.body()?.data?.let {
                    emit(BaseResult.Success(it))
                }
            }else{
                val error = Gson().fromJson(getComment.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun deleteComment(commentId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            val delete = commentRepository.deleteComment(commentId)
            if(delete.isSuccessful && delete.code() == 200){
                emit(
                    BaseResult.Success(
                        delete.isSuccessful
                    )
                )
            }else{
                val error = Gson().fromJson(delete.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }
}
