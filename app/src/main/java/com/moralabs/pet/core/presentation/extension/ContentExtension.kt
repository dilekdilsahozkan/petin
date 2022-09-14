package com.moralabs.pet.core.presentation.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() = view?.let { activity?.hideKeyboard(it) }

fun Activity.hideKeyboard() = hideKeyboard(currentFocus ?: View(this))

fun Context.hideKeyboard(view: View) =
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken, 0)

internal inline fun <reified T : Any> Fragment.argument(key: String): Lazy<T?> {
    return lazy(LazyThreadSafetyMode.NONE) {
        arguments?.get(key) as? T
    }
}