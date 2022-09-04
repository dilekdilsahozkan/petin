package com.moralabs.pet.core.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.databinding.ActivityBaseBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity

abstract class BaseActivity<Binding : ViewDataBinding> : AppCompatActivity(), PetToolbarListener {

    protected lateinit var binding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    private val loadingImage by lazy {
        PetLoading(this)
    }

    @LayoutRes
    protected open fun getLayoutId() = R.layout.activity_base

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments

        if (fragments.size > 0) {
            (fragments[fragments.size - 1] as? BaseFragment<*, *, *>)?.onFragmentResumed()
        }

        super.onBackPressed()
    }

    open fun stopLoading() {
        (binding.root as? ViewGroup)?.removeView(loadingImage)
    }

    open fun startLoading() {
        if (loadingImage.parent == null) {
            (binding.root as? ViewGroup)?.addView(loadingImage)
        }
    }

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.img_back -> super.onBackPressed()
            R.id.img_select -> startActivity(Intent(this, MainPageActivity::class.java))
        }
    }

    override fun setNotification(count: Int, type: Int) {}

    override fun showTitleText(title: String?) {
        (binding as? ActivityBaseBinding)?.appBar?.showTitleText(title)
    }
    override fun visibilityChange() {}

    override fun showTitleLogo() {}

    override fun showLightColorBar() {
        (binding as? ActivityBaseBinding)?.appBar?.showLightColorBar()
    }

}