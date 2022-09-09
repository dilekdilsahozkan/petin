package com.moralabs.pet.onboarding.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

typealias LoginAction = (() -> Unit)

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(),
    PetToolbarListener {

    companion object {
        const val RESULT_LOGIN = "loginResult"
    }

    override fun getLayoutId() = R.layout.activity_login

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.img_back -> super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.appBar)
    }
}

class LoginResultContract : ActivityResultContract<LoginAction, LoginResult>() {

    private var action: LoginAction? = null

    override fun createIntent(context: Context, action: LoginAction): Intent {
        this.action = action

        return Intent(context, LoginActivity::class.java)
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

enum class LoginResult(val id: Int) : Parcelable {

    LOGIN_OK(0),
    LOGIN_CANCELED(2);

    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginResult> {
        override fun createFromParcel(parcel: Parcel): LoginResult {
            return values()[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<LoginResult?> {
            return arrayOfNulls(size)
        }
    }
}