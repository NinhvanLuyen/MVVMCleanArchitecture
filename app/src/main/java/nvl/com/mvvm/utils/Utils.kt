package nvl.com.mvvm.utils

import java.util.*

object Utils {
    fun getShortNumberFormat(number: Int): String {
        return when {
            number >= 1000000000 -> when {
                number % 1000000000 >= 100000000 -> "${number / 1000000000}.${number % 1000000000 / 10000000}B"
                number % 1000000000 >= 10000000 -> "${number / 1000000000}.0${number % 1000000000 / 10000000}B"
                else -> "${number / 1000000000}B"
            }
            number >= 1000000 -> when {
                number % 1000000 >= 100000 -> "${number / 1000000}.${number % 1000000 / 10000}M"
                number % 1000000 >= 10000 -> "${number / 1000000}.0${number % 1000000 / 10000}M"
                else -> "${number / 1000000}M"
            }
            number >= 10000 -> "${number / 1000}K"
            else -> getNumberFormat(number)
        }
    }

    fun getNumberFormat(num: Int): String {
        var number = num
        var negative = false
        if (number < 0) {
            negative = true
            number = number * -1
        }
        val strNum = number.toString()
        if (strNum.length <= 3) {
            return if (negative)
                "-$strNum"
            else
                strNum
        } else {
            var result = ""
            val loop = strNum.length / 3
            var start = strNum.length % 3
            for (i in 0..loop) {
                if (i == 0 || result.isEmpty()) {
                    if (start > 0) {
                        result = strNum.substring(0, start)
                    } else {
                        start += 3
                    }
                } else {
                    result += "," + strNum.substring(start, start + 3)
                    start += 3
                }
            }
            return if (negative)
                "-$result"
            else
                result
        }
    }
    fun getTime(): Long = GregorianCalendar.getInstance().time.time

}