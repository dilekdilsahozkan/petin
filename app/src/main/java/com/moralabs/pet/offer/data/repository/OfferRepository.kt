package com.moralabs.pet.offer.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.EmptyDto
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.offer.data.remote.dto.OfferRequestDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import retrofit2.Response

interface OfferRepository {
    suspend fun makeOffer(newOfferRequest: OfferRequestDto): Response<BaseResponse<List<PetDto>>>
    suspend fun getOffer(offerId: String?): Response<BaseResponse<OfferDto>>
    suspend fun usersOffer(postId: String?): Response<BaseResponse<List<OfferDto>>>
    suspend fun declineOffer(offerId: String?): Response<EmptyDto>
    suspend fun acceptOffer(offerId: String?): Response<EmptyDto>
}