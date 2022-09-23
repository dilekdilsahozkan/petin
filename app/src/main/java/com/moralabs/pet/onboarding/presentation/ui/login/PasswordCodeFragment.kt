package com.moralabs.pet.onboarding.presentation.ui.login

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.databinding.FragmentPasswordCodeBinding
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.PasswordCodeDto
import com.moralabs.pet.onboarding.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordCodeFragment : View.OnClickListener, BaseFragment<FragmentPasswordCodeBinding, LoginDto, LoginViewModel>() {

    var boxList = mutableListOf<EditText>()
    private lateinit var timer: CountDownTimer

    private val passwordActivity by lazy {
        activity as? LoginActivity
    }

    override fun getLayoutId() = R.layout.fragment_password_code
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<LoginDto> {
        val viewModel: LoginViewModel by viewModels()
        return viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        boxList.add(binding.otp1)
        boxList.add(binding.otp2)
        boxList.add(binding.otp3)
        boxList.add(binding.otp4)
        boxList.add(binding.otp5)
        boxList.add(binding.otp6)

        addListeners()
        startTimer()

        binding.buttonContinue.isClickable = false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonContinue -> {
                var otp = ""
                boxList.forEach { otp += it.text.toString() }
                viewModel.passwordCode(
                    PasswordCodeDto(
                        code = otp
                    )
                )
                passwordActivity?.getPassword()?.code = otp
            }
            R.id.timeUp -> {
                startTimer()
            }
        }    }

    override fun addListeners() {
        super.addListeners()

        binding.buttonContinue.setOnClickListener(this)
        binding.timeUp.setOnClickListener(this)

        boxList.forEachIndexed { index, box ->

            (box).onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                if (b) {
                    val drawable = view.background as GradientDrawable
                    drawable.setStroke(2, ContextCompat.getColor(context!!, R.color.mainColor))
                } else {
                    val drawable = view.background as GradientDrawable
                    drawable.setStroke(2, ContextCompat.getColor(context!!, R.color.transparentWhite))
                }
            }

            box.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (event.action != KeyEvent.ACTION_DOWN)
                    return@OnKeyListener true
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    binding.buttonContinue.alpha = 0.5f
                    binding.buttonContinue.isClickable = false
                    box.setText("")
//                    box.clearFocus()
//                    if (index > 0) {
//                        boxList[index - 1].requestFocus()
//                        if (boxList[index - 1].text.isNotEmpty()) boxList[index - 1].setSelection(boxList[index - 1].text.length)
//                    }
                    return@OnKeyListener true
                }
                return@OnKeyListener false
            })

            box.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 0) {
//                        box.clearFocus()
//                        if (index > 0)
//                            boxList[index - 1].requestFocus()
                    } else {
                        if (s?.length == 2) box.setText(s.toString().substring(1, 2))

                        box.clearFocus()
                        if (index < boxList.size - 1) {
                            boxList[index + 1].requestFocus()
                            if (boxList[index + 1].text.isNotEmpty()) boxList[index + 1].setSelection(boxList[index + 1].text.length)
                        }
                    }
                    binding.buttonContinue.alpha = if (isOtpCompleted()) 1f else 0.5f
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        }
    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.forgotState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<*> -> {
                        findNavController().navigate(
                            R.id.action_fragment_code_to_newPasswordFragment
                        )
                    }
                    is ViewState.Error<*> -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_password_code),
                            Toast.LENGTH_LONG
                        ).show()
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun startTimer() {
        binding.timeUp.visibility = View.GONE
        binding.secondsLeft.visibility = View.VISIBLE
        timer = object : CountDownTimer(180 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.secondsLeft.text =
                    "${millisUntilFinished.toInt() / 1000} ${getString(R.string.secondsLeft)}"
            }

            override fun onFinish() {
                binding.secondsLeft.visibility = View.GONE
                binding.timeUp.visibility = View.VISIBLE
            }
        }
        timer.start()
    }

    fun isOtpCompleted(): Boolean {
        binding.buttonContinue.isClickable = false
        boxList.forEach { box ->
            if ((box as? EditText)?.text?.length == 0)
                return false
        }
        binding.buttonContinue.isClickable = true
        return true
    }
}
