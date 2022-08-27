package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMainPageBinding
import com.moralabs.pet.mainPage.data.remote.dto.ContentTypeDto
import com.moralabs.pet.mainPage.presentation.viewmodel.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainPageFragment : BaseFragment<FragmentMainPageBinding, List<PostDto>, MainPageViewModel>(){

    var list = mutableListOf<ContentTypeDto>()
    override fun getLayoutId() = R.layout.fragment_main_page
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<PostDto>> {
        val viewModel: MainPageViewModel by viewModels()
        return viewModel
    }

    private val postAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PostListAdapter(
            onRowClick = { position, post ->
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postAdapter
            setHasFixedSize(true)
        }
        viewModel.feedPost()
     //   adapter.setItems()
    }

    override fun stateSuccess(data: List<PostDto>) {
        super.stateSuccess(data)

        postAdapter.submitList(data)
    }
}