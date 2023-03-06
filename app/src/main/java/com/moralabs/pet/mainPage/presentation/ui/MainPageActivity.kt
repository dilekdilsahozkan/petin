package com.moralabs.pet.mainPage.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.observable.NotificationHandler
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityMainPageBinding
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetFragment
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetListener
import com.moralabs.pet.newPost.presentation.ui.NewPostActivity
import com.moralabs.pet.newPost.presentation.ui.TabTextType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>(),
    PetToolbarListener, ChooseTypeBottomSheetListener {

    @Inject
    lateinit var notificationHandler: NotificationHandler

    private lateinit var navController: NavController

    override fun getLayoutId() = R.layout.activity_main_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_main_page) as NavHostFragment

        navController = navHostFragment.navController

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
        addObservers()

        notificationHandler.checkNotifications()
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
            val result = if(item.itemId == R.id.home) true else loginIfNeeded {}

            if (result) {
                if (binding.dashboardNavigation.selectedItemId == item.itemId && item.itemId == R.id.home) {
                    MainPageFragment.instance?.scrollToTop()
                }
                NavigationUI.onNavDestinationSelected(item, navController)
            }
            result
        }
    }

    fun addObservers() {
        notificationHandler.hasNotification.observe(this) {
            if (it) {
                val badge =
                    binding.dashboardNavigation.getOrCreateBadge(R.id.notification)
                badge.isVisible = true
                badge.backgroundColor = getColor(R.color.mainColor)
            } else {
                val badgeDrawable = binding.dashboardNavigation.getBadge(R.id.notification)
                badgeDrawable?.isVisible = false
                badgeDrawable?.clearNumber()
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