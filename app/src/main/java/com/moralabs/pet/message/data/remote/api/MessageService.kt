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
import retrofit2.http.Path

interface MessageService {

    @GET("/chat")
    suspend fun chat(): Response<BaseResponse<List<ChatDto>>>

    @GET("/chat/user/{userId}")
    suspend fun getChatDetail(@Path("userId") userId: String?): Response<BaseResponse<ChatDto>>

    @POST("/chat/user/{userId}")
    suspend fun newChat(): Response<BaseResponse<ChatDto>>

    @POST("/chat/user/{userId}/message")
    suspend fun sendNewChat(@Path("userId") userId: String?, @Body sendChat: ChatRequestDto): Response<BaseResponse<Nothing>>

    @POST("/chat/user/{chatId}/{dateTime}")
    suspend fun chatDateTime(): Response<BaseResponse<MessageDto>>
}