package com.moralabs.pet.offer.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
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
            val offerValue = offerRepository.makeOffer(newOfferRequest)
            if(offerValue.isSuccessful && offerValue.code() == 200){
                emit(
                    BaseResult.Success(
                        CreateOfferDto(
                            offerValue = offerValue.body()?.data
                        )
                    )
                )
            }else{
                val error = Gson().fromJson(offerValue.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun getOffer(offerId: String?): Flow<BaseResult<OfferDetailDto>> {
        return flow {
            val readOffer = offerRepository.getOffer(offerId)
            if(readOffer.isSuccessful && readOffer.code() == 200){
                emit(
                    BaseResult.Success(
                        OfferDetailDto(
                            readOffer = readOffer.body()?.data
                        )
                    )
                )
            }else{
                val error = Gson().fromJson(readOffer.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun usersOffer(postId: String?): Flow<BaseResult<OfferDetailDto>> {
        return flow {
            val allOffers = offerRepository.usersOffer(postId)
            if(allOffers.isSuccessful && allOffers.code() == 200){
                emit(
                    BaseResult.Success(
                        OfferDetailDto(
                            allOffers = allOffers.body()?.data
                        )
                    )
                )
            }else{
                val error = Gson().fromJson(allOffers.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun declineOffer(offerId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            val decline = offerRepository.declineOffer(offerId)
            if(decline.isSuccessful && decline.code() == 200){
                emit(BaseResult.Success(true))
            }else{
                val error = Gson().fromJson(decline.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun acceptOffer(offerId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            val accept = offerRepository.acceptOffer(offerId)
            if(accept.isSuccessful && accept.code() == 200){
                emit(BaseResult.Success(true))
            }else{
                val error = Gson().fromJson(accept.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun petValue(): Flow<BaseResult<CreateOfferDto>> {
        return flow {
            val getOffer = petRepository.petPost()
            if(getOffer.isSuccessful && getOffer.code() == 200){
                emit(
                    BaseResult.Success(
                        CreateOfferDto(
                            getOffer = getOffer.body()?.data ?: listOf()
                        )
                    )
                )
            }else{
                val error = Gson().fromJson(getOffer.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }
}