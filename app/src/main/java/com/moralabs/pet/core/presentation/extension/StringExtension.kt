package com.moralabs.pet.core.presentation.extension

fun String?.isEmptyOrBlank(): Boolean {
    this?.let {
        return it.isEmpty() && it.isBlank()
    }

    return false
}

fun String?.isNotEmptyOrBlank(): Boolean {
    this?.let {
        return it.isEmptyOrBlank().not()
    }

    return false
}