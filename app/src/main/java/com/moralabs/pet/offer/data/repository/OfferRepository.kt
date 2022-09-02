package com.moralabs.pet.offer.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response

interface OfferRepository {
    suspend fun newMakeOffer(newOffer: OfferDto): Response<BaseResponse<List<PetDto>>>
}