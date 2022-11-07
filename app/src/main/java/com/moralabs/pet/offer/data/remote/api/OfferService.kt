package com.moralabs.pet.offer.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.EmptyDto
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.offer.data.remote.dto.OfferRequestDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response
import retrofit2.http.*

interface OfferService {
    @POST("/offer")
    suspend fun newOffer(@Body makeOfferRequest: OfferRequestDto): Response<BaseResponse<List<PetDto>>>

    @GET("/offer")
    suspend fun myOffers(): Response<BaseResponse<List<OfferDto>>>

    @GET("/offer/{offerId}")
    suspend fun getOffer(@Path("offerId") offerId: String?): Response<BaseResponse<OfferDto>>

    @GET("/offer/post/{postId}")
    suspend fun usersOffer(@Path("postId") postId: String?): Response<BaseResponse<List<OfferDto>>>

    @POST("/offer/{offerId}/accept")
    suspend fun acceptOffer(@Path("offerId") offerId: String?): Response<EmptyDto>

    @POST("/offer/{offerId}/decline")
    suspend fun declineOffer(@Path("offerId") offerId: String?): Response<EmptyDto>

    @DELETE("/offer/{offerId}/delete")
    suspend fun deleteOffer(@Path("offerId") offerId: String?): Response<EmptyDto>
}