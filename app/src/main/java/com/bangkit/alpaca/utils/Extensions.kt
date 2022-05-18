package com.bangkit.alpaca.utils

import java.text.DateFormat
import java.util.*

/**
 * Convert the Unix Timestamp to formatted String
 *
 * @return String
 */
fun Long.toFormattedString(): String {
    val date = Date(this)
    return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale("id", "ID")).format(date)
}