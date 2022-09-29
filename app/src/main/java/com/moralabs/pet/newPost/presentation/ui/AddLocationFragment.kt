package com.moralabs.pet.newPost.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostLocationDto
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentAddLocationBinding
import com.moralabs.pet.newPost.presentation.viewmodel.LocationViewModel
import com.moralabs.pet.onboarding.presentation.ui.login.LoginResultContract
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

    override fun addListeners() {
        super.addListeners()

        binding.cities.setOnItemClickListener { adapterView, _, position, _ ->
            binding.cities.setText(
                (adapterView.getItemAtPosition(position) as? PostLocationDto)?.name,
                false
            )
            viewModel.city.postValue(adapterView.getItemAtPosition(position) as? PostLocationDto)
        }
    }

    override fun addObservers() {
        super.addObservers()

        viewModel.city.observe(viewLifecycleOwner) {
            binding.cities.setText(it?.name, false)
        }
    }

    override fun stateSuccess(data: List<PostLocationDto>) {
        super.stateSuccess(data)

        binding.save.setOnClickListener {

            if (viewModel.city.value != null && binding.cities.text?.isNotBlank() == true) {
                val bundle = bundleOf(
                    NewPostActivity.LOCATION to viewModel.city
                )
                val intent = Intent(context, NewPostActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        data.let {
            cityAdapter.clear()
            cityAdapter.addAll(data.toMutableList())
        }
    }

    private val locationResultLauncher =
        registerForActivityResult(
            LocationResultContract()
        )
        { result ->
            when (result) {

                else -> {}
            }
        }
}