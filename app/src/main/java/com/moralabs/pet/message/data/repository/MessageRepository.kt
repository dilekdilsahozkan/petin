package com.moralabs.pet.message.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.message.data.remote.dto.ChatDto
import com.moralabs.pet.message.data.remote.dto.ChatRequestDto
import com.moralabs.pet.profile.data.remote.dto.UserDto
import retrofit2.Response

interface MessageRepository {
    suspend fun getChat(): Response<BaseResponse<List<ChatDto>>>
    suspend fun getChatDetail(userId: String?): Response<BaseResponse<ChatDto>>
    suspend fun sendMessage(userId: String?, chatRequestDto: ChatRequestDto): Response<BaseResponse<Nothing>>
    suspend fun searchUser(keyword: String): Response<BaseResponse<List<UserDto>>>
}