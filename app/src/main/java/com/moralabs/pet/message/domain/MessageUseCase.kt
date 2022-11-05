package com.moralabs.pet.message.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.domain.*
import com.moralabs.pet.message.data.remote.dto.ChatDto
import com.moralabs.pet.message.data.remote.dto.ChatRequestDto
import com.moralabs.pet.message.data.repository.MessageRepository
import com.moralabs.pet.profile.data.remote.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) : BaseUseCase() {

    override fun execute(): Flow<BaseResult<List<ChatDto>>> {
        return flow {
            val postValue = messageRepository.getChat()
            emit(
                BaseResult.Success(
                    postValue.body()?.data ?: listOf()
                )
            )
        }
    }

    fun getMessage(userId: String?): Flow<BaseResult<ChatDto>> {
        return flow {
            val postValue = messageRepository.getChatDetail(userId)
            if (postValue.isSuccessful && postValue.code() == 200) {
                emit(
                    BaseResult.Success(
                        postValue.body()?.data ?: ChatDto()
                    )
                )
            } else {
                val error = Gson().fromJson(postValue.errorBody()?.string(), BaseResponse::class.java)
                if (error.code != ServerErrorCode.CHAT_NOT_FOUND.value) {
                    emit(
                        BaseResult.Error(
                            ErrorResult(
                                code = ErrorCode.SERVER_ERROR,
                                error.userMessage
                            )
                        )
                    )
                } else {
                    emit(
                        BaseResult.Success(ChatDto())
                    )
                }
            }
        }
    }

    fun sendMessage(userId: String?, chatRequestDto: ChatRequestDto): Flow<BaseResult<ChatDto>> {
        return flow {
            val postValue = messageRepository.sendMessage(userId, chatRequestDto)
            if (postValue.isSuccessful && postValue.code() == 200) {
                emit(
                    BaseResult.Success(
                        postValue.body()?.data ?: ChatDto()
                    )
                )
            } else {
                val error = Gson().fromJson(postValue.errorBody()?.string(), BaseResponse::class.java)
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

    fun searchUser(keyword: String): Flow<BaseResult<List<UserDto>>> {
        return flow {
            val postValue = messageRepository.searchUser(keyword)
            if (postValue.isSuccessful && postValue.code() == 200) {
                emit(
                    BaseResult.Success(
                        postValue.body()?.data ?: listOf()
                    )
                )
            } else {
                val error = Gson().fromJson(postValue.errorBody()?.string(), BaseResponse::class.java)
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