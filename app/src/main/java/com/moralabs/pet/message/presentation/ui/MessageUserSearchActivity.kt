package com.moralabs.pet.message.presentation.ui

import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityMessageUserSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageUserSearchActivity : BaseActivity<ActivityMessageUserSearchBinding>(),
    PetToolbarListener {

    override fun getLayoutId() = R.layout.activity_message_user_search

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.img_back -> super.onBackPressed()
        }
    }
}