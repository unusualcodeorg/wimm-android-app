package com.whereismymotivation.utils.common

import java.util.*

object Formatter {

    private val suffixes = TreeMap<Long, String>().apply {
        put(1_000L, "k");
        put(1_000_000L, "M");
        put(1_000_000_000L, "G");
        put(1_000_000_000_000L, "T");
        put(1_000_000_000_000_000L, "P");
        put(1_000_000_000_000_000_000L, "E");
    }

    fun format(number: Long): String {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (number == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1)
        if (number < 0) return "-" + format(-number)
        if (number < 1000) return number.toString() //deal with easy case

        return suffixes.floorEntry(number)?.run {
            val divideBy = key
            val suffix = value

            val truncated = number / (divideBy / 10) //the number part of the output times 10
            val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
            if (hasDecimal) "${truncated / 10.0}$suffix" else "${truncated / 10}$suffix"
        } ?: ""
    }
}