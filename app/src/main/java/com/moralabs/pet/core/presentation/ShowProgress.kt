package com.moralabs.pet.core.presentation

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.moralabs.pet.R

class ShowProgress(context: Context) : Dialog(context) {

    init {
        dialog = Dialog(context)
    }
    fun showPopup(){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog,null,false)
        dialog?.setCancelable(false)
        dialog?.setContentView(dialogView)
        dialog?.show()
    }
    companion object{
        var dialog:Dialog? = null
        fun dismissPopup() = dialog?.dismiss()
    }
}