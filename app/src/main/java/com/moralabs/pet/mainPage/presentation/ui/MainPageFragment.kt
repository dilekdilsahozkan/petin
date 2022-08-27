package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMainPageBinding
import com.moralabs.pet.mainPage.data.remote.dto.ContentTypeDto
import com.moralabs.pet.mainPage.presentation.viewmodel.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : BaseFragment<FragmentMainPageBinding, PostDto, MainPageViewModel>(){

    var list = mutableListOf<ContentTypeDto>()
    override fun getLayoutId() = R.layout.fragment_main_page
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<PostDto> {
        val viewModel: MainPageViewModel by viewModels()
        return viewModel
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PostListAdapter(
            onRowClick = { position, post ->
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = adapter
            setHasFixedSize(true)
        }
        adapter.setItems(viewModel.feedPost())
    }
}