package com.moralabs.pet.offer.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response
import retrofit2.http.*

interface OfferService {
    @POST("/offer")
    suspend fun newOffer(@Body makeOffer: OfferDto): Response<BaseResponse<List<PetDto>>>

    @GET("/offer/{offerId}")
    suspend fun getOffer(@Path("offerId") OfferId: String?): Response<BaseResponse<OfferDto>>

    @DELETE("/offer/{offerId}/decline")
    suspend fun declineOffer(@Path("offerId") OfferId: String?): Response<BaseResponse<OfferDto>>

    @POST("/offer/{offerId}/accept")
    suspend fun acceptOffer(@Path("offerId")OfferId: String?): Response<BaseResponse<Nothing>>
}