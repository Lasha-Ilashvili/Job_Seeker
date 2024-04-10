package com.example.job_seeker.presentation.extension

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


fun String.toPresentation(): String {
    val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    val date = serverDateFormat.parse(this) ?: return ""

    val localDateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a", Locale.US).apply {
        timeZone = TimeZone.getDefault()
    }

    return localDateFormat.format(date)
}