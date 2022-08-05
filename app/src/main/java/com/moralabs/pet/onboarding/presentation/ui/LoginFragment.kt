package com.moralabs.pet.onboarding.presentation.ui

import android.content.Intent
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.databinding.FragmentLoginBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginRequestDto
import com.moralabs.pet.onboarding.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginDto, LoginViewModel>() {

    override fun getLayoutId() = R.layout.fragment_login
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<LoginDto> {
        val viewModel: LoginViewModel by viewModels()
        return viewModel
    }
    override fun addListeners() {
        super.addListeners()
        binding.loginButton.setOnClickListener {
            if(binding.emailEdittext.text.toString().isNotEmpty() && binding.passwordEdittext.text.toString().isNotEmpty()){
                startActivity(Intent(context, MainPageActivity::class.java))
                viewModel.login(LoginRequestDto(
                    this.email_edittext.toString(),
                    this.password_edittext.toString()))
            }
        }
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_login_to_forgotPasswordFragment)

        }
        setRegisterClickable()
    }
    fun setRegisterClickable(){
        val spannableString = SpannableString(getString(R.string.dontHaveAccount))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(context, RegisterActivity::class.java))
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = Color.parseColor("#FF724C")
            }
        }
        spannableString.setSpan(clickableSpan, spannableString.length-10, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.haveAccountText.text = spannableString
        binding.haveAccountText.movementMethod = LinkMovementMethod.getInstance()
    }
}