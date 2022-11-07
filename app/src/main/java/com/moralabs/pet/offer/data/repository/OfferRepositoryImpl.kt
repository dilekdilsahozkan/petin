package com.moralabs.pet.offer.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.offer.data.remote.api.OfferService
import com.moralabs.pet.offer.data.remote.dto.OfferRequestDto
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(private val service: OfferService) :
    OfferRepository, BaseRepository {
    override suspend fun makeOffer(newOfferRequest: OfferRequestDto) = service.newOffer(newOfferRequest)
    override suspend fun myOffers() = service.myOffers()
    override suspend fun getOffer(offerId: String?) = service.getOffer(offerId)
    override suspend fun usersOffer(postId: String?) = service.usersOffer(postId)
    override suspend fun declineOffer(offerId: String?) = service.declineOffer(offerId)
    override suspend fun acceptOffer(offerId: String?) = service.acceptOffer(offerId)
    override suspend fun deleteOffer(offerId: String?) = service.deleteOffer(offerId)
}