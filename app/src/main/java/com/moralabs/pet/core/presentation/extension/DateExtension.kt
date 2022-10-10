package com.moralabs.pet.core.presentation.extension

import android.content.Context
import android.text.format.DateUtils
import com.moralabs.pet.R
import java.text.SimpleDateFormat
import java.util.*

fun Long?.toFullDate(context: Context?): String {
    this?.let {
        if (DateUtils.isToday(it))
            return context?.getString(R.string.today) + ", " + SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            ).format(Date(it))
        else if (it.isYesterday())
            return context?.getString(R.string.yesterday) + ", " + SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            ).format(Date(it))

        return SimpleDateFormat("dd MMMM", Locale.getDefault()).format(Date(it))
    }
    return ""
}

fun Long?.toDbDate(): String {
    this?.let {
        val date = Date(this)
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }
    return ""
}

fun Long?.toHour(): String {
    this?.let {
        val date = Date(this)
        val format = SimpleDateFormat("HH:mm")
        return format.format(date)
    }
    return ""
}

fun String?.dbToFullDate(context: Context?): String {
    this?.let {
        val time = SimpleDateFormat("yyyy-MM-dd").parse(it).time
        if (DateUtils.isToday(time))
            return context?.getString(R.string.today) ?: ""
        else if (time.isYesterday())
            return context?.getString(R.string.yesterday) ?: ""

        return SimpleDateFormat("dd MMMM", Locale.getDefault()).format(Date(time))
    }
    return ""
}

fun Long?.isYesterday(): Boolean {
    this?.let {
        return DateUtils.isToday(it + DateUtils.DAY_IN_MILLIS)
    }
    return false
}