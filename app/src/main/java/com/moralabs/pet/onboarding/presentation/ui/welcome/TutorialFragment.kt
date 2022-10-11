package com.moralabs.pet.onboarding.presentation.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.EmptyViewModel
import com.moralabs.pet.databinding.FragmentTutorialBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialFragment :
    BaseFragment<FragmentTutorialBinding, Nothing, Nothing>() {

    companion object {
        const val TEXT = "text"
        const val LOTTIE = "lottie"
    }

    private val text by lazy {
        arguments?.getInt(TEXT)
    }

    private val lottie by lazy {
        arguments?.getInt(LOTTIE)
    }

    override fun getLayoutId() = R.layout.fragment_tutorial
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text?.let {
            binding.tutorialText.setText(it)
        }

        lottie?.let {
            binding.lottie.setAnimation(it)
        }
    }

    override fun fragmentViewModel(): BaseViewModel<Nothing> {
        val viewModel: EmptyViewModel by viewModels()
        return viewModel
    }
}