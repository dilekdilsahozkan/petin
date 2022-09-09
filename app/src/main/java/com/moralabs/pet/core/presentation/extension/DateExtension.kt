package com.moralabs.pet.core.presentation.extension

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
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

fun Long?.toDate(): String {
    this?.let {
        val date = Date(this)
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
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

    return ""
}

fun Long?.isYesterday(): Boolean {
    this?.let {
        return DateUtils.isToday(it + DateUtils.DAY_IN_MILLIS)
    }

    return false
}

/**
 * @sample 20221220 first 4 char is year, 2 char month, 2 char day
 * @return dot formated 20.12.2022, null if date8 is null or not 8 length
 */
fun String?.from8toDate() = if (this == null || this.length != 8) {
    null
} else {
    "${this.substring(6, 8)}.${this.substring(4, 6)}.${this.substring(0, 4)}"
}

fun Date.isYesterday(): Boolean {
    return DateUtils.isToday(time + DateUtils.DAY_IN_MILLIS)
}

fun Date.isToday(): Boolean {
    return DateUtils.isToday(time)
}

fun Long?.offerDate(): String? {
    this?.let {
        val date = Date(this)
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("tr", "TR"))
        return format.format(date)
    }

    return ""
}

// Converts current date to proper provided format
fun Date.convertTo(format: String): String? {
    var dateStr: String? = null
    val df = SimpleDateFormat(format)
    try {
        dateStr = df.format(this)
    } catch (ex: Exception) {
        Log.d("date", ex.toString())
    }

    return dateStr
}

// Converts current date to Calendar
fun Date.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

fun Date.isFuture(): Boolean {
    return !Date().before(this)
}

fun Date.isPast(): Boolean {
    return Date().before(this)
}

fun Date.isTomorrow(): Boolean {
    return DateUtils.isToday(this.time - DateUtils.DAY_IN_MILLIS)
}

fun Date.today(): Date {
    return Date()
}

fun Date.yesterday(): Date {
    val cal = this.toCalendar()
    cal.add(Calendar.DAY_OF_YEAR, -1)
    return cal.time
}

fun Date.tomorrow(): Date {
    val cal = this.toCalendar()
    cal.add(Calendar.DAY_OF_YEAR, 1)
    return cal.time
}

fun Date.hour(): Int {
    return this.toCalendar().get(Calendar.HOUR)
}

fun Date.minute(): Int {
    return this.toCalendar().get(Calendar.MINUTE)
}

fun Date.second(): Int {
    return this.toCalendar().get(Calendar.SECOND)
}

fun Date.month(): Int {
    return this.toCalendar().get(Calendar.MONTH) + 1
}

fun Date.monthName(locale: Locale? = Locale.getDefault()): String {
    return this.toCalendar().getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
}

fun Date.year(): Int {
    return this.toCalendar().get(Calendar.YEAR)
}

fun Date.day(): Int {
    return this.toCalendar().get(Calendar.DAY_OF_MONTH)
}

fun Date.dayOfWeek(): Int {
    return this.toCalendar().get(Calendar.DAY_OF_WEEK)
}

fun Date.dayOfWeekName(locale: Locale? = Locale.getDefault()): String {
    return this.toCalendar().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale)
}

fun Date.dayOfYear(): Int {
    return this.toCalendar().get(Calendar.DAY_OF_YEAR)
}

fun Date.screenFormat(): String {
    val sdf = SimpleDateFormat("dd/MM/yyy", Locale.getDefault())
    return sdf.format(time)
}