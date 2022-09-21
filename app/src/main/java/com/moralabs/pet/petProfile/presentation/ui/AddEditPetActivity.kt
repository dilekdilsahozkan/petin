package com.moralabs.pet.petProfile.presentation.ui

import android.os.Bundle
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityAddPetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditPetActivity : BaseActivity<ActivityAddPetBinding>() {

    companion object {
        const val BUNDLE_PET = "Pet"
    }

    override fun getLayoutId() = R.layout.activity_add_pet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.appBar)
    }

    override fun showTitleText(title: String?) {
        binding.appBar.showTitleText(title)
    }

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.img_back -> super.onBackPressed()
        }
    }
}