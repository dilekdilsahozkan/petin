package com.moralabs.pet.core.domain

import com.moralabs.pet.core.data.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : BaseUseCase() {

    fun isLoggedIn(): Boolean {
        return authenticationRepository.isLoggedIn()
    }

    fun logout(){
        authenticationRepository.logout()
    }
}