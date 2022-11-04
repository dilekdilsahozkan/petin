package com.moralabs.pet.onboarding.presentation.ui.register

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.databinding.FragmentRegisterBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.onboarding.data.remote.dto.RegisterDto
import com.moralabs.pet.onboarding.data.remote.dto.RegisterRequestDto
import com.moralabs.pet.onboarding.presentation.ui.login.LoginActivity
import com.moralabs.pet.onboarding.presentation.ui.login.LoginResult
import com.moralabs.pet.onboarding.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterDto, RegisterViewModel>() {

    private val isFromAction by lazy {
        activity?.intent?.getBooleanExtra(LoginActivity.BUNDLE_ACTION, false) ?: false
    }

    override fun getLayoutId() = R.layout.fragment_register
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<RegisterDto> {
        val viewModel: RegisterViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText("")
    }

    override fun addListeners() {
        super.addListeners()
        setRegisterClickable()
        binding.registerButton.setOnClickListener {
            if (binding.agreementRadioButton.isChecked) {
                viewModel.register(
                    RegisterRequestDto(
                        binding.nameSurnameEdittext.text.toString(),
                        binding.usernameEdittext.text.toString(),
                        binding.emailEdittext.text.toString(),
                        binding.passwordEdittext.text.toString()
                    )
                )
            }
        }
        binding.agreementRead.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_register_to_agreementFragment)
        }
    }

    override fun stateSuccess(data: RegisterDto) {
        super.stateSuccess(data)

        if (isFromAction) {
            activity?.setResult(
                Activity.RESULT_OK,
                Intent().run { putExtras(bundleOf(LoginActivity.RESULT_LOGIN to LoginResult.LOGIN_OK)) })
            activity?.finish()
        } else {
            startActivity(Intent(context, MainPageActivity::class.java))
            activity?.finish()
        }
    }

    override fun stateError(data: String?) {
        super.stateError(data)

        binding.passwordRule.visibility = View.VISIBLE
    }

    private fun setRegisterClickable() {
        val spannableString = SpannableString(getString(R.string.haveAccount))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(context, LoginActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = Color.parseColor("#FF724C")
            }
        }
        spannableString.setSpan(
            clickableSpan,
            spannableString.length - 10,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.dontHaveAccountText.text = spannableString
        binding.dontHaveAccountText.movementMethod = LinkMovementMethod.getInstance()
    }
}