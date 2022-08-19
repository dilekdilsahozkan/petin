package com.moralabs.pet.core.presentation.toolbar

interface PetToolbarListener {
    fun onItemSelected(id: Int)
    fun showTitleText(title: String?)
    fun showTitleLogo()
    fun setNotification(count: Int, type: Int)
    fun showLightColorBar()
    fun visibilityChange()
}