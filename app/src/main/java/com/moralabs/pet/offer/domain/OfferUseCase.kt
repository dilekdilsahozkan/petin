package com.moralabs.pet.offer.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.offer.data.remote.dto.OfferDto
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

    fun newMakeOffer(newOffer: OfferDto): Flow<BaseResult<CreateOfferDto>> {
        return flow {
            val offerValue = offerRepository.makeOffer(newOffer).body()?.data ?: listOf()
            emit(
                BaseResult.Success(
                    CreateOfferDto(
                        offerValue = offerValue
                    )
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