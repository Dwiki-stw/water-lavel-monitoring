package com.dwiki.nodemcu.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun provideDateNow(): String {
    val now = Date()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.US)

    return dateFormat.format(now)
}