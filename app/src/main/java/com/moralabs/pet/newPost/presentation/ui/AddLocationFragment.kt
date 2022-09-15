package com.moralabs.pet.newPost.presentation.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostLocationDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentAddLocationBinding
import com.moralabs.pet.newPost.presentation.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLocationFragment :
    BaseFragment<FragmentAddLocationBinding, List<PostLocationDto>, LocationViewModel>() {

    override fun getLayoutId() = R.layout.fragment_add_location
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<PostLocationDto>> {
        val viewModel: LocationViewModel by viewModels()
        return viewModel
    }

    private val cityAdapter: ArrayAdapter<PostLocationDto> by lazy {

        object : ArrayAdapter<PostLocationDto>(requireContext(), R.layout.list_item) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                (view as? TextView)?.text = getItem(position)?.name

                return view
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_addLocation_to_postFragment)
        }

        binding.cities.setAdapter(cityAdapter)
        viewModel.getLocation()
    }

    override fun stateSuccess(data: List<PostLocationDto>) {
        super.stateSuccess(data)

        data.let {
            cityAdapter.clear()
            cityAdapter.addAll(data.toMutableList() ?: mutableListOf())
        }
    }
}