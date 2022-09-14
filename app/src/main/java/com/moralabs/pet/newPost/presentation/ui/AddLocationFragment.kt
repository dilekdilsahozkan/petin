package com.moralabs.pet.newPost.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.LocationDto
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentAddLocationBinding
import com.moralabs.pet.databinding.FragmentMainPageBinding
import com.moralabs.pet.databinding.ListItemBinding
import com.moralabs.pet.mainPage.presentation.viewmodel.MainPageViewModel

class AddLocationFragment :
    BaseFragment<FragmentAddLocationBinding, List<PostDto>, MainPageViewModel>() {

    override fun getLayoutId() = R.layout.fragment_add_location

    override fun fragmentViewModel(): BaseViewModel<List<PostDto>> {
        val viewModel: MainPageViewModel by viewModels()
        return viewModel
    }
}