package com.moralabs.pet.core.presentation.extension

import androidx.core.text.HtmlCompat

fun String?.isEmptyOrBlank(): Boolean {
    this?.let {
        return it.isEmpty() && it.isBlank()
    }
    return false
}

internal fun String?.fromHtml() =
    this?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }

fun String?.isNotEmptyOrBlank(): Boolean {
    this?.let {
        return it.isEmptyOrBlank().not()
    }
    return false
}