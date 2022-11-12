package com.planradar.weather.utils
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    /**
     * Pass your date format and no of days for minus from current
     * If you want to get previous date then pass days with minus sign
     * else you can pass as it is for next date
     * @param dateInMillis
     * @return Formatted Date DD.MM.YYYY - 24:00
     */
    fun getDateFormatted(dateInMillis: Long): String {
        val s = SimpleDateFormat("dd.MM.yyyy. - HH:mm", Locale.US)
        return s.format(dateInMillis)
    }

}