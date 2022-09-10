package com.moralabs.pet.message.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
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
            emit(
                BaseResult.Success(
                    postValue.body()?.data ?: ChatDto()
                )
            )
        }
    }

    fun sendMessage(userId: String?, chatRequestDto: ChatRequestDto): Flow<BaseResult<ChatDto>> {
        return flow {
            val postValue = messageRepository.sendMessage(userId, chatRequestDto)
            emit(
                BaseResult.Success(
                    postValue.body()?.data ?: ChatDto()
                )
            )
        }
    }

    fun searchUser(keyword: String): Flow<BaseResult<List<UserDto>>> {
        return flow {
            val postValue = messageRepository.searchUser(keyword)
            emit(
                BaseResult.Success(
                    postValue.body()?.data ?: listOf()
                )
            )
        }
    }
}