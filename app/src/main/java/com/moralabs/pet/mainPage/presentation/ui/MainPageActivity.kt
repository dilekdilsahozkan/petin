package com.moralabs.pet.mainPage.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.ActivityMainPageBinding
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetFragment
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetListener
import com.moralabs.pet.newPost.presentation.ui.NewPostActivity
import com.moralabs.pet.newPost.presentation.ui.TabTextType
import com.moralabs.pet.notification.presentation.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>(),
    PetToolbarListener, ChooseTypeBottomSheetListener {

    private lateinit var navController: NavController
    var viewModel: NotificationViewModel? = null

    override fun getLayoutId() = R.layout.activity_main_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[NotificationViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_main_page) as NavHostFragment

        navController = navHostFragment.navController
        viewModel?.latestNotification()

        setSupportActionBar(binding.appBar)
        setupActionBarWithNavController(
            navHostFragment.navController, AppBarConfiguration.Builder(
                R.id.home,
                R.id.messages,
                R.id.addButton,
                R.id.notification,
                R.id.profile,
            )
                .build()
        )
        addListeners()
        observe()
    }

    private fun addListeners() {

        binding.addPostButton.setOnClickListener {
            loginIfNeeded {
                ChooseTypeBottomSheetFragment(
                    this@MainPageActivity
                ).show(supportFragmentManager, "")
            }
        }

        binding.dashboardNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.notification -> {
                    val badgeDrawable = binding.dashboardNavigation.getBadge(R.id.notification)
                    badgeDrawable?.isVisible = false
                    badgeDrawable?.clearNumber()
                }
            }
            val result = loginIfNeeded {}

            if (result) {
                NavigationUI.onNavDestinationSelected(item, navController)
            }
            result
        }
    }

    private fun observe() {

        lifecycleScope.launch {
            viewModel?.latestState?.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<*> -> {
                        if (it.data as Boolean) {
                            val badge =
                                binding.dashboardNavigation.getOrCreateBadge(R.id.notification)
                            badge.isVisible = true
                            badge.backgroundColor = getColor(R.color.mainColor)
                        }
                    }
                    is ViewState.Error<*> -> {
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }

    }

    override fun onItemClick(type: Int) {

        startActivity(Intent(this@MainPageActivity, NewPostActivity::class.java).apply {
            this.putExtras(
                bundleOf(
                    NewPostActivity.BUNDLE_CHOOSE_TYPE to when (type) {
                        0 -> TabTextType.POST_TYPE.type
                        1 -> TabTextType.QAN_TYPE.type
                        2 -> TabTextType.FIND_PARTNER_TYPE.type
                        3 -> TabTextType.ADOPTION_TYPE.type
                        else -> 0
                    }
                )
            )
        })
    }
}