package com.moralabs.pet.petProfile.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentPetBinding
import com.moralabs.pet.databinding.ItemPetCardBinding
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.presentation.viewmodel.PetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetFragment : BaseFragment<FragmentPetBinding, List<PetDto>, PetViewModel>() {

    private val petId: String? by lazy {
        activity?.intent?.getStringExtra(PetProfileActivity.PET_ID)
    }

    override fun getLayoutId() = R.layout.fragment_pet
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    private val petAdapter: BaseListAdapter<PetDto, ItemPetCardBinding> by lazy {
        BaseListAdapter(R.layout.item_pet_card, BR.item, onRowClick = {
            val bundle = bundleOf(
                PetProfileActivity.PET_ID to it.id
            )
            val intent = Intent(context, PetProfileActivity::class.java)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }, isSameDto = { oldItem, newItem ->
            true
        })
    }

    override fun fragmentViewModel(): BaseViewModel<List<PetDto>> {
        val viewModel: PetViewModel by viewModels()
        return viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = petAdapter
        binding.addPet.setOnClickListener {
            val intent = Intent(context, AddPetActivity::class.java)
            context?.startActivity(intent)
        }
        viewModel.getPet()
    }

    override fun addObservers() {
        super.addObservers()
        lifecycleScope.launch {
            viewModel.deleteState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<*> -> {
                        viewModel.deletePet(petId)
                    }
                    is ViewState.Error<*> -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_pet_delete),
                            Toast.LENGTH_LONG
                        ).show()
                        stopLoading()
                    }
                }
            }
        }
    }

    override fun stateSuccess(data: List<PetDto>) {
        super.stateSuccess(data)

        petAdapter.submitList(data)
    }
}