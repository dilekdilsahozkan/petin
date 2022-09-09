package com.moralabs.pet.offer.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.EmptyDto
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.offer.data.remote.dto.OfferRequestDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import retrofit2.Response
import retrofit2.http.*

interface OfferService {
    @POST("/offer")
    suspend fun newOffer(@Body makeOfferRequest: OfferRequestDto): Response<BaseResponse<List<PetDto>>>

    @GET("/offer/{offerId}")
    suspend fun getOffer(@Path("offerId") offerId: String?): Response<BaseResponse<OfferDto>>

    @GET("/offer/post/{postId}")
    suspend fun usersOffer(@Path("postId") postId: String?): Response<BaseResponse<List<OfferDto>>>

    @DELETE("/offer/{offerId}/decline")
    suspend fun declineOffer(@Path("offerId") offerId: String?): Response<EmptyDto>

    @POST("/offer/{offerId}/accept")
    suspend fun acceptOffer(@Path("offerId") offerId: String?): Response<EmptyDto>
}