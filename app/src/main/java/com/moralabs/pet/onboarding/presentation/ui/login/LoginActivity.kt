package com.moralabs.pet.onboarding.presentation.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.os.bundleOf
import com.facebook.FacebookSdk
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityLoginBinding
import com.moralabs.pet.onboarding.data.remote.dto.NewPasswordDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize

typealias LoginAction = (() -> Unit)

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(),
    PetToolbarListener {

    companion object {
        const val RESULT_LOGIN = "loginResult"
        const val BUNDLE_ACTION = "FROM_LOGIN"
    }

    private var newPassword: NewPasswordDto = NewPasswordDto()

    override fun getLayoutId() = com.moralabs.pet.R.layout.activity_login

    override fun onItemSelected(id: Int) {
        when (id) {
            com.moralabs.pet.R.id.img_back -> super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FacebookSdk.sdkInitialize(this)

        setSupportActionBar(binding.appBar)
    }

    fun getPassword(): NewPasswordDto {
        return newPassword
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            for (fragment in supportFragmentManager.fragments) {
                fragment.onActivityResult(requestCode, resultCode, data)
                Log.d("Activity", "ON RESULT CALLED")
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
    }
}

class LoginResultContract : ActivityResultContract<LoginAction, LoginResult>() {

    private var action: LoginAction? = null

    override fun createIntent(context: Context, action: LoginAction): Intent {
        this.action = action

        return Intent(context, LoginActivity::class.java).apply { putExtras(bundleOf(LoginActivity.BUNDLE_ACTION to true)) }
    }

    override fun parseResult(resultCode: Int, result: Intent?): LoginResult {
        if (resultCode != Activity.RESULT_OK) {
            return LoginResult.LOGIN_CANCELED
        }

        val loginResult = result?.getParcelableExtra<LoginResult>(LoginActivity.RESULT_LOGIN)

        if (loginResult == LoginResult.LOGIN_OK) {
            this.action?.invoke()
        }

        return loginResult ?: LoginResult.LOGIN_CANCELED
    }
}

@Parcelize
enum class LoginResult : Parcelable {
    LOGIN_OK, LOGIN_CANCELED
}