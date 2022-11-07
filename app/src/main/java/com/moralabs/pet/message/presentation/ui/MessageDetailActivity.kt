package com.moralabs.pet.message.presentation.ui

import android.os.Bundle
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityMessageDetailBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageDetailActivity : BaseActivity<ActivityMessageDetailBinding>(),
    PetToolbarListener {

    companion object {
        const val BUNDLE_USER = "user"
        const val USER_ID = "userId"
    }

    private val userDto by lazy {
        intent?.getParcelableExtra<UserDto>(BUNDLE_USER)
    }

    override fun getLayoutId() = R.layout.activity_message_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userDto?.let {
            binding.appBar.setUser(userDto)
        }

        setSupportActionBar(binding.appBar)
    }

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.img_back -> super.onBackPressed()
        }
    }

    fun setUser(userDto: UserDto?) {
        binding.appBar.setUser(userDto)
    }
}