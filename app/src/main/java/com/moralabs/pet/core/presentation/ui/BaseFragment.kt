package com.moralabs.pet.core.presentation.ui

import android.app.Activity
import android.content.ContextWrapper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.core.domain.BaseDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

abstract class BaseFragment<T : ViewDataBinding,
        U : BaseDto,
        K : BaseViewModel<U>> : Fragment() {

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


}