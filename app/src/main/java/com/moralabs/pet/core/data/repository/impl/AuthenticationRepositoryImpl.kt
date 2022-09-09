package com.moralabs.pet.core.data.repository.impl

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.AuthenticationDto
import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.profile.data.remote.dto.UserDto

class AuthenticationRepositoryImpl(context: Context?) : AuthenticationRepository {

    companion object {
        private const val USER_KEY = "user"
    }

    private var authentication: AuthenticationDto? = null
    private var _user: UserDto = UserDto()

    private val preferences by lazy {
        context?.let {
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

            EncryptedSharedPreferences.create(
                "encrypted_preferences",
                masterKeyAlias,
                it,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    init {
        preferences?.getString(USER_KEY, null)?.let {
            authentication = Gson().fromJson(it, AuthenticationDto::class.java)
        }

        if(authentication == null) {
            authentication = AuthenticationDto()

            preferences?.edit()?.putString(USER_KEY, Gson().toJson(authentication))?.commit()
        }
    }

    override fun isLoggedIn() = authentication?.bearerKey == null

    override fun logout(): Boolean {
        authentication?.apply {
            this.bearerKey = null

            preferences?.edit()?.putString(USER_KEY, Gson().toJson(authentication))?.commit()
        }

        return true
    }

    override fun login(bearerToken: String): Boolean {
        authentication?.apply {
            this.bearerKey = bearerToken

            preferences?.edit()?.putString(USER_KEY, Gson().toJson(authentication))
        }

        return true
    }

    override fun getUser() = _user

    override fun requestHeaders() = authentication?.let {
        hashMapOf(
            "Authorization" to if(it.bearerKey == null) null else "Bearer ${it.bearerKey}",
            "Accept-Language" to it.language,
            "Pet-Channel" to it.channel,
            "Pet-DeviceModel" to it.deviceModel,
            "Pet-DeviceBrand" to it.deviceBrand,
            "Pet-DeviceVersion" to it.deviceVersion,
            "Pet-ApplicationId" to it.applicationId,
            "Pet-ApplicationVersion" to it.applicationVersion,
        )
    } ?: run {
        hashMapOf()
    }
}