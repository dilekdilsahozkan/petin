package com.moralabs.pet.message.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.message.data.remote.api.MessageService
import com.moralabs.pet.message.data.remote.dto.ChatRequestDto
import com.moralabs.pet.profile.data.remote.api.UserService
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val service: MessageService,
    private val userService: UserService
) :
    MessageRepository, BaseRepository {
    override suspend fun getChat() = service.chat()
    override suspend fun getChatDetail(userId: String?) = service.getChatDetail(userId)
    override suspend fun sendMessage(userId: String?, chatRequestDto: ChatRequestDto) = service.sendNewChat(userId, chatRequestDto)
    override suspend fun searchUser(keyword: String) = userService.searchUser(keyword)
}