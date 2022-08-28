package com.moralabs.pet.profile.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.profile.data.remote.api.PetService
import com.moralabs.pet.profile.data.remote.dto.PetDto
import retrofit2.Response
import javax.inject.Inject

class PetRepositoryImpl  @Inject constructor(private val service: PetService) :
    PetRepository, BaseRepository {
    override suspend fun petPost(): Response<BaseResponse<List<PetDto>>> = service.getPet()
}