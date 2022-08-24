package com.moralabs.pet.newPost.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityNewPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPostActivity : BaseActivity<ActivityNewPostBinding>(),
PetToolbarListener{

    companion object {
        fun newIntent(context: Context) = Intent(context, NewPostActivity::class.java)
    }

    override fun getLayoutId() = R.layout.activity_new_post

}