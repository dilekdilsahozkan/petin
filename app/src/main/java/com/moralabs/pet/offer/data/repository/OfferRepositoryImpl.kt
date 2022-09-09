package com.moralabs.pet.offer.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.offer.data.remote.api.OfferService
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.offer.data.remote.dto.OfferRequestDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import retrofit2.Response
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(private val service: OfferService) :
    OfferRepository, BaseRepository {
    override suspend fun makeOffer(newOfferRequest: OfferRequestDto): Response<BaseResponse<List<PetDto>>> = service.newOffer(newOfferRequest)
    override suspend fun getOffer(offerId: String?): Response<BaseResponse<OfferDto>> = service.getOffer(offerId)
    override suspend fun usersOffer(postId: String?): Response<BaseResponse<List<OfferDto>>> = service.usersOffer(postId)
}