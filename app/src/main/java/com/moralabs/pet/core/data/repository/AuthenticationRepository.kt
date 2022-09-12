package com.moralabs.pet.core.data.repository

import com.moralabs.pet.core.data.remote.dto.AuthenticationDto

interface AuthenticationRepository {
    fun isLoggedIn(): Boolean
    fun logout(): Boolean
    fun login(userId: String?, bearerToken: String): Boolean
    fun getAuthentication() : AuthenticationDto?
    fun requestHeaders(): HashMap<String, String?>
}