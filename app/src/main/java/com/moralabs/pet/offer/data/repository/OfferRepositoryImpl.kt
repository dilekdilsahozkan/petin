package com.moralabs.pet.offer.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.offer.data.remote.api.OfferService
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import retrofit2.Response
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(private val service: OfferService) :
    OfferRepository, BaseRepository {
    override suspend fun makeOffer(newOffer: OfferDto): Response<BaseResponse<List<PetDto>>> = service.newOffer(newOffer)
}