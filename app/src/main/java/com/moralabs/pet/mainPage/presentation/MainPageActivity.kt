package com.moralabs.pet.mainPage.presentation

import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseActivity
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.databinding.ActivityMainPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>() ,
    PetToolbarListener {

    override fun getLayoutId() = R.layout.activity_main_page

}