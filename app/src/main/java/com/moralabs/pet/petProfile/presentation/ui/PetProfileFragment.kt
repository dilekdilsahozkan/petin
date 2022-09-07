package com.moralabs.pet.petProfile.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.BR
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentPetProfileBinding
import com.moralabs.pet.databinding.ItemPetFeatureBinding
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.presentation.viewmodel.PetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetProfileFragment : BaseFragment<FragmentPetProfileBinding, List<PetDto>, PetViewModel>() {

    private val petId: String? by lazy {
        activity?.intent?.getStringExtra(PetProfileActivity.PET_ID)
    }

    override fun getLayoutId() = R.layout.fragment_pet_profile
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<PetDto>> {
        val viewModel: PetViewModel by viewModels()
        return viewModel
    }

    private val attributeAdapter: BaseListAdapter<PetAttributeDto, ItemPetFeatureBinding> by lazy {
        BaseListAdapter(R.layout.item_pet_feature, BR.item, onRowClick = {

        }, isSameDto = { oldItem, newItem ->
            true
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPet()
    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.attributeState.collect {
                when (it) {
                    is ViewState.Success<*> -> {
                        Toast.makeText(requireContext(), getString(R.string.qna), Toast.LENGTH_LONG).show()
                        attributeAdapter.currentList.forEach { it.isLoading = false }
                        attributeAdapter.submitList(attributeAdapter.currentList)
                    }
                    is ViewState.Error<*> -> {
                        Toast.makeText(requireContext(), getString(R.string.findPartner), Toast.LENGTH_LONG).show()
                        attributeAdapter.currentList.forEach { it.isLoading = false }
                        attributeAdapter.submitList(attributeAdapter.currentList)
                    }
                }
            }
        }
    }

    override fun stateSuccess(data: List<PetDto>) {
        super.stateSuccess(data)
/*
        data.forEach {
            when (it.type) {
                4 -> binding.locationName.text = data.getPet.petAttributes.
            }
        }*/
    }
}