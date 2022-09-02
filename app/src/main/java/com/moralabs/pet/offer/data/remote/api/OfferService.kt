package com.moralabs.pet.offer.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OfferService {
    @POST("/offer")
    suspend fun newOffer(@Body makeOffer: OfferDto): Response<BaseResponse<List<PetDto>>>
}