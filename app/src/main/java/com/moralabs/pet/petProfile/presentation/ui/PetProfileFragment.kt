package com.moralabs.pet.petProfile.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.BR
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.databinding.FragmentPetProfileBinding
import com.moralabs.pet.databinding.ItemPetFeatureBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.presentation.viewmodel.PetProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetProfileFragment : BaseFragment<FragmentPetProfileBinding, PetDto, PetProfileViewModel>() {

    private val petId: String? by lazy {
        activity?.intent?.getStringExtra(PetProfileActivity.PET_ID)
    }
    private val otherUserId: String? by lazy {
        activity?.intent?.getStringExtra(PetProfileActivity.OTHER_USER_ID)
    }

    override fun getLayoutId() = R.layout.fragment_pet_profile
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<PetDto> {
        val viewModel: PetProfileViewModel by viewModels()
        return viewModel
    }

    private val attributeAdapter: BaseListAdapter<PetAttributeDto, ItemPetFeatureBinding> by lazy {
        BaseListAdapter(R.layout.item_pet_feature, BR.item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.attributeRecycler.adapter = attributeAdapter
        if (!otherUserId.isNullOrBlank()) {
            viewModel.getAnotherUserPetInfo(petId, otherUserId)
            binding.editIcon.visibility = View.GONE
            binding.deleteIcon.visibility = View.GONE
        } else {
            viewModel.petInfo(petId)
        }
    }

    override fun addListeners() {
        super.addListeners()

        binding.deleteIcon.setOnClickListener {
            PetWarningDialog(
                requireContext(),
                PetWarningDialogType.CONFIRMATION,
                resources.getString(R.string.ask_sure),
                okey = getString(R.string.yes),
                description = resources.getString(R.string.delete_pet_warning),
                negativeButton = resources.getString(R.string.no),
                onResult = {
                    if (PetWarningDialogResult.OK == it) {
                        viewModel.deletePet(petId)
                    }
                }
            ).show()
        }

        binding.editIcon.setOnClickListener {
            context?.startActivity(
                Intent(
                    context,
                    AddEditPetActivity::class.java
                ).apply { putExtras(bundleOf(AddEditPetActivity.BUNDLE_PET to viewModel.latestDto)) })
        }
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
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.pet_delete),
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(context, MainPageActivity::class.java))
                    }
                    is ViewState.Error<*> -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_pet_delete),
                            Toast.LENGTH_LONG
                        ).show()
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun stateSuccess(data: PetDto) {
        super.stateSuccess(data)

        attributeAdapter.submitList(data.petAttributes)

        binding.petName.text = data.name.toString()
        binding.petImage.loadImage(data.media?.url)

        data.petAttributes?.forEach {
            when (it.type) {

                8 -> binding.petGender.text = it.choice.toString()

                5 -> binding.locationName.text = it.choice.toString()

                7 -> binding.petType.text = it.choice.toString()

                9 -> binding.petAge.text = it.choice.toString()
            }
        }
    }
}