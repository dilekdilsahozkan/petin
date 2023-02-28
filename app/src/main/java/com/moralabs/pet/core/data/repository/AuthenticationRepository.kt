package com.moralabs.pet.core.data.repository

import com.moralabs.pet.core.data.remote.dto.AuthenticationDto

interface AuthenticationRepository {
    fun isLoggedIn(): Boolean
    fun isGuest(): Boolean
    fun logout(): Boolean

    fun checkLoginState()
    fun login(userId: String?, bearerToken: String, refreshToken: String): Boolean
    fun guestLogin(bearerToken: String): Boolean
    fun refreshLogin(bearerToken: String?, refreshToken: String?): Boolean
    fun getAuthentication() : AuthenticationDto?
    fun requestHeaders(): HashMap<String, String?>
}