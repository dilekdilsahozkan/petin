package com.moralabs.pet.onboarding.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.ActivityUsernameBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.onboarding.data.remote.dto.ExternalLoginDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONException


@AndroidEntryPoint
class UsernameActivity : BaseActivity<ActivityUsernameBinding>(),
    PetToolbarListener {

    override fun getLayoutId() = R.layout.activity_username

    private val viewModel: LoginViewModel by viewModels()
    lateinit var callbackManager: CallbackManager

    val accessToken = AccessToken.getCurrentAccessToken()
    val isLoggedIn = accessToken != null && !accessToken.isExpired
    val token = accessToken?.token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()

        Log.d("token", " $token")
        Log.d("isLoggedIn", " $isLoggedIn")

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email, public_profile"))
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {

                override fun onCancel() {
                    Log.d("facebookLogin", " -> onCancel")
                }

                override fun onError(exception: FacebookException) {
                    Log.d("facebookLogin", " -> onError")
                }

                override fun onSuccess(result: LoginResult) {
                    Log.d("facebookLogin", " -> onSuccess")
                    Log.d("FBLOGIN", result.accessToken.token.toString())
                    Log.d("FBLOGIN", result.recentlyDeniedPermissions.toString())
                    Log.d("FBLOGIN", result.recentlyGrantedPermissions.toString())

                    val request = GraphRequest.newMeRequest(result.accessToken) { `object`, response ->
                        try {
                            if(`object`?.has("id") == true){
                                val email = `object`.getString("email")
                                val name = `object`.getString("name")
                                Log.d("FBEmail", email)
                                Log.d("FBName", name)
                            }

                        }catch (e: JSONException) {
                            Toast.makeText(
                                this@UsernameActivity,
                                "Facebook Authentication Failed.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", "id,name,email")
                    request.parameters = parameters
                    request.executeAsync()
                }
            })

        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<LoginDto> -> {
                        startActivity(Intent(applicationContext, MainPageActivity::class.java))
                        stopLoading()
                    }
                    is ViewState.Error<*> -> {
                        stateError(it.message)
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    fun addListeners(){

        binding.nextButton.setOnClickListener {
            viewModel.externalLogin(
                ExternalLoginDto(
                    externalAuthToken = accessToken?.token.toString(),
                    userName = binding.emailEdittext.text.toString(),
                    type = ExternalType.FACEBOOK.type
                )
            )
        }
    }
}

enum class ExternalType(val type: Int) {
    FACEBOOK(1),
    GOOGLE(2)
}
