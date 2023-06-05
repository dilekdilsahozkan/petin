package com.moralabs.pet.core.presentation.ui

import android.app.Activity
import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.domain.AuthenticationUseCase
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.notification.domain.NotificationUseCase
import com.moralabs.pet.onboarding.presentation.ui.login.LoginAction
import com.moralabs.pet.onboarding.presentation.ui.login.LoginResultContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding,
        U : Any,
        K : BaseViewModel<U>> : Fragment() {

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    enum class UseCaseFetchStrategy {
        NO_FETCH,
        ON_CREATED,
        ON_START,
        ON_RESUME
    }

    protected lateinit var binding: T
    protected val viewModel by lazy {
        fragmentViewModel() as K
    }

    protected val toolbarListener by lazy {
        activity as? PetToolbarListener
    }

    protected val currentActivity by lazy {
        (context as? ContextWrapper)?.baseContext as? Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is ViewState.Success<U> -> stateSuccess(it.data)
                    is ViewState.Error<U> -> {
                        it.message?.let { message ->
                            stateError(message)
                        } ?: run {
                            stateError(it.error?.toString())
                        }
                    }
                    is ViewState.Idle<U> -> stopLoading()
                    is ViewState.Loading<U> -> startLoading()
                }
            }
        }

        addListeners()
        addObservers()

        CoroutineScope(Dispatchers.Unconfined).launch {
            notificationUseCase.sendNotificationToken().collect {}
        }

        return binding?.root
    }

    override fun onStart() {
        super.onStart()

        setToolbar()

        if (fetchStrategy() == UseCaseFetchStrategy.ON_START) viewModel.fetch()
    }

    override fun onResume() {
        super.onResume()

        onFragmentResumed()
    }

    fun onFragmentResumed() {
        if (fetchStrategy() == UseCaseFetchStrategy.ON_RESUME) viewModel.fetch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (fetchStrategy() == UseCaseFetchStrategy.ON_CREATED) viewModel.fetch()
    }

    open fun stateSuccess(data: U) {
        stopLoading()
    }

    open fun stateError(data: String?) {
        if(data == null){
            Toast.makeText(requireContext(), "", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(requireContext(), data, Toast.LENGTH_LONG).show()
        }
        stopLoading()
    }

    open fun stopLoading() {
        (activity as? BaseActivity<*>)?.stopLoading()
    }

    open fun startLoading() {
        (activity as? BaseActivity<*>)?.startLoading()
    }

    open fun addListeners() {

    }

    open fun addObservers() {

    }

    open fun setToolbar() {

    }

    open fun fetchStrategy() = UseCaseFetchStrategy.ON_CREATED

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun fragmentViewModel(): BaseViewModel<U>
    // LOGIN AREA
    @Inject
    open lateinit var authenticationUseCase: AuthenticationUseCase

    private val loginResultLauncher =
        registerForActivityResult(
            LoginResultContract()
        )
        { result ->
            when (result) {
                else -> {}
            }
        }

    fun loginIfNeeded(
        action: LoginAction,
    ) {
        if (authenticationUseCase.isGuest()) {
            PetWarningDialog(
                requireContext(),
                PetWarningDialogType.LOGIN,
                getString(R.string.register),
                okay = getString(R.string.ok),
                discard = getString(R.string.no),
                description = getString(R.string.loginNeeded),
                positiveButton = getString(R.string.login),
                onResult = {
                    if (PetWarningDialogResult.OK == it) {
                        loginResultLauncher.launch(action)
                    }
                }).show()
        } else {
            action.invoke()
        }
    }
}