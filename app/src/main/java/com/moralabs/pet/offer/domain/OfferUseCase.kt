package com.moralabs.pet.offer.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.offer.data.remote.dto.OfferDetailDto
import com.moralabs.pet.offer.data.remote.dto.OfferRequestDto
import com.moralabs.pet.offer.data.repository.OfferRepository
import com.moralabs.pet.petProfile.data.remote.dto.CreateOfferDto
import com.moralabs.pet.petProfile.data.repository.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OfferUseCase @Inject constructor(
    private val offerRepository: OfferRepository,
    private val petRepository: PetRepository
) : BaseUseCase() {

    fun newMakeOffer(newOfferRequest: OfferRequestDto): Flow<BaseResult<CreateOfferDto>> {
        return flow {
            val offerValue = offerRepository.makeOffer(newOfferRequest).body()?.data ?: listOf()
            emit(
                BaseResult.Success(
                    CreateOfferDto(
                        offerValue = offerValue
                    )
                )
            )
        }
    }

    fun getOffer(offerId: String?): Flow<BaseResult<OfferDetailDto>>  {
        return flow {
           val readOffer = offerRepository.getOffer(offerId).body()?.data
                emit(
                    BaseResult.Success(
                        OfferDetailDto(
                            readOffer = readOffer
                        )
                    )
                )
        }
    }

    fun usersOffer(postId : String?): Flow<BaseResult<OfferDetailDto>>  {
        return flow {
           val allOffers =  offerRepository.usersOffer(postId).body()?.data ?: listOf()
                emit(
                    BaseResult.Success(
                        OfferDetailDto(
                            allOffers = allOffers
                        )
                    )
                )
        }
    }

    fun declineOffer(offerId : String?): Flow<BaseResult<Boolean>>  {
        return flow {
            emit(
                BaseResult.Success(
                    offerRepository.declineOffer(offerId).isSuccessful
                )
            )
        }
    }

    fun acceptOffer(offerId : String?): Flow<BaseResult<Boolean>>   {
        return flow {
            emit(
                BaseResult.Success(
                    offerRepository.acceptOffer(offerId).isSuccessful
                )
            )
        }
    }

    fun petValue(): Flow<BaseResult<CreateOfferDto>> {
        return flow {
            val getOffer = petRepository.petPost().body()?.data ?: listOf()
            emit(
                BaseResult.Success(
                    CreateOfferDto(
                        getOffer = getOffer
                    )
                )
            )
        }
    }
}