package com.moralabs.pet.mainPage.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityMainPageBinding
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetFragment
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetListener
import com.moralabs.pet.newPost.presentation.ui.NewPostActivity
import com.moralabs.pet.newPost.presentation.ui.TabTextType
import com.moralabs.pet.notification.presentation.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>(),
    PetToolbarListener, ChooseTypeBottomSheetListener {

    override fun getLayoutId() = R.layout.activity_main_page
    var viewModel: NotificationViewModel? = null

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[NotificationViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_main_page) as NavHostFragment

        navController = navHostFragment.navController

      val badge = binding.dashboardNavigation.getOrCreateBadge(R.id.notification)
        val badgeDrawable = binding.dashboardNavigation.getBadge(R.id.notification)

        badgeDrawable?.isVisible = true
        badge.backgroundColor = getColor(R.color.mainColor)
        badge.isVisible = true
      //  badge.verticalOffset = 20
      //  badge.horizontalOffset = 15

         /* itemCount.observe(this) { productCount ->
            if (productCount > 0) {
                binding.dashboardNavigation.getOrCreateBadge(R.id.notification).apply {
                    isVisible = true
                    number = productCount
                }
            } else {
                binding.dashboardNavigation.getBadge(R.id.notification)?.apply {
                    isVisible = false
                    clearNumber()
                }
            }
        }*/

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
    }

    private fun addListeners() {
        binding.addPostButton.setOnClickListener {
            loginIfNeeded {
                ChooseTypeBottomSheetFragment(
                    this@MainPageActivity
                ).show(supportFragmentManager, "")
            }
        }

        binding.dashboardNavigation.setOnItemSelectedListener {
            val result = loginIfNeeded {}

            if (result) {
                NavigationUI.onNavDestinationSelected(it, navController)
            }

            result
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