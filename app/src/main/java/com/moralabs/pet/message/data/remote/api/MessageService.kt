package com.moralabs.pet.message.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.message.data.remote.dto.ChatDto
import com.moralabs.pet.message.data.remote.dto.ChatRequestDto
import com.moralabs.pet.message.data.remote.dto.ChatResponse
import com.moralabs.pet.message.data.remote.dto.MessageDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageService {

    @GET("/chat")
    suspend fun chat(): Response<BaseResponse<ChatDto>>

    @POST("/chat/{userId}")
    suspend fun newChat(): Response<BaseResponse<ChatDto>>

    @POST("/chat/{userId}/message")
    suspend fun sendNewChat(@Body sendChat : ChatRequestDto): Response<ChatResponse>

    @POST("/chat/{chatId}/{dateTime}")
    suspend fun chatDateTime(): Response<BaseResponse<MessageDto>>
}