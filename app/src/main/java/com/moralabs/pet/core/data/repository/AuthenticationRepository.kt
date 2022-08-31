package com.moralabs.pet.core.data.repository

interface AuthenticationRepository {
    fun isLoggedIn(): Boolean
    fun logout(): Boolean
    fun login(bearerToken: String): Boolean
    fun requestHeaders(): HashMap<String, String?>
}