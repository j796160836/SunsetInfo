package com.johnny.sunsetinfo

import java.text.SimpleDateFormat
import java.util.*

object SunsetDateUtil {
    val dateFormater = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.US)
        .apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
}