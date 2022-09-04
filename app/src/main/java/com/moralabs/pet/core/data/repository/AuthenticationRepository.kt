package com.moralabs.pet.core.data.repository

import com.moralabs.pet.profile.data.remote.dto.UserDto

interface AuthenticationRepository {
    fun isLoggedIn(): Boolean
    fun logout(): Boolean
    fun login(bearerToken: String): Boolean
    fun getUser() : UserDto
    fun requestHeaders(): HashMap<String, String?>
}