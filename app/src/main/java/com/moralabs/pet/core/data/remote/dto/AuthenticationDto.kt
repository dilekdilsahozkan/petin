package com.moralabs.pet.core.data.remote.dto

import android.os.Build
import com.moralabs.pet.BuildConfig
import java.util.*
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation

data class AuthenticationDto(
    var userId: String? = null,
    var bearerKey: String? = null,
    var email: String? = null,
    var refreshKey: String? = null,
    var language: String = Locale.getDefault().language,
    var loginState: UserState = UserState.IDLE,
    val channel: String = "android",
    val deviceModel: String? = Build.MODEL,
    val deviceBrand: String? = Build.BRAND,
    var deviceVersion: String? = Build.VERSION.SDK_INT.toString(),
    var applicationId: String? = BuildConfig.APPLICATION_ID,
    var applicationVersion: String? = BuildConfig.VERSION_NAME,
    var challenge: ChallengeContinuation? = null
)

enum class UserState {
    IDLE,
    PROCESSING,
    UNSET
}