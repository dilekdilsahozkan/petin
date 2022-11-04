package com.moralabs.pet.petProfile.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentPetBinding
import com.moralabs.pet.databinding.ItemPetCardBinding
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.presentation.viewmodel.PetViewModel
import com.moralabs.pet.profile.presentation.ui.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetFragment : BaseFragment<FragmentPetBinding, List<PetDto>, PetViewModel>() {

    private val otherUserId: String? by lazy {
        activity?.intent?.getStringExtra(ProfileActivity.OTHER_USER_ID)
    }

    override fun getLayoutId() = R.layout.fragment_pet
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<PetDto>> {
        val viewModel: PetViewModel by viewModels()
        return viewModel
    }

    private val petAdapter: BaseListAdapter<PetDto, ItemPetCardBinding> by lazy {
        BaseListAdapter(R.layout.item_pet_card, BR.item, onRowClick = {
            it.id?.let { petId ->
                openPetProfile(petId)
            }
        }, isSameDto = { oldItem, newItem ->
            oldItem == newItem
        }, emptyString = getString(R.string.noPet))
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            getPetList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = petAdapter
        binding.addPet.setOnClickListener {
            openAddPet()
        }
        getPetList()
    }

    override fun stateSuccess(data: List<PetDto>) {
        super.stateSuccess(data)
        petAdapter.submitList(data)
        petAdapter.notifyDataSetChanged()
    }

    private fun getPetList() {
        if (!otherUserId.isNullOrBlank()) {
            viewModel.getAnotherUserPet(otherUserId)
            binding.addPetTitleAndIcon.visibility = View.GONE
        } else {
            viewModel.getPet()
        }
    }

    private fun openAddPet() {
        val intent = Intent(requireContext(), AddEditPetActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun openPetProfile(petId: String) {
        val intent = Intent(requireContext(), PetProfileActivity::class.java)
        intent.putExtras(
            bundleOf(
                PetProfileActivity.PET_ID to petId,
                PetProfileActivity.OTHER_USER_ID to otherUserId
            )
        )
        resultLauncher.launch(intent)
    }
}