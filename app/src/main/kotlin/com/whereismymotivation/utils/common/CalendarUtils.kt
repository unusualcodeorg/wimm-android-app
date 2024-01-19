package com.whereismymotivation.utils.common

import com.whereismymotivation.utils.log.Logger
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


object CalendarUtils {

    private const val TAG = "CalendarUtils"

    fun now(): Date = Calendar.getInstance().time

    fun getTodayFormattedDate(): String =
        try {
            "Today, " + SimpleDateFormat("EEEE dd", Locale.ENGLISH).format(Date())
        } catch (e: Exception) {
            Logger.e(TAG, e.toString())
            "Today"
        }

    fun getFormattedCurrentTime(): String =
        try {
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date())
        } catch (e: Exception) {
            Logger.e(TAG, e.toString())
            "Current Time"
        }

    fun getFormattedDate(date: Date): String? =
        try {
            SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(date)
        } catch (e: Exception) {
            Logger.e(TAG, e.toString())
            null
        }

    fun getFormattedTime(date: Date): String? =
        try {
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(date)
        } catch (e: Exception) {
            Logger.e(TAG, e.toString())
            null
        }

    fun getFormattedDateInDayAndMonth(date: Date): String =
        try {
            SimpleDateFormat("dd MMM", Locale.ENGLISH).format(date)
        } catch (e: Exception) {
            Logger.e(TAG, e.toString())
            ""
        }

    fun parseDayMonthYear(millis: Long): Triple<String, String, String>? =
        try {
            val calendar = Calendar.getInstance()
            calendar.time.time = millis

            val day = SimpleDateFormat("dd", Locale.ENGLISH).format(calendar.time)
            val month = SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.time)
            val year = SimpleDateFormat("YYYY", Locale.ENGLISH).format(calendar.time)

            Triple(day, month, year)
        } catch (e: Exception) {
            Logger.e(TAG, e.toString())
            null
        }

    fun parseDay(date: Date): String? =
        try {
            SimpleDateFormat("dd", Locale.ENGLISH).format(date)
        } catch (e: Exception) {
            Logger.e(TAG, e.toString())
            null
        }

    fun parseMonth(date: Date): String? =
        try {
            SimpleDateFormat("MMMM", Locale.ENGLISH).format(date)
        } catch (e: Exception) {
            Logger.e(TAG, e.toString())
            null
        }

    fun parseYear(date: Date): String? =
        try {
            SimpleDateFormat("YYYY", Locale.ENGLISH).format(date)
        } catch (e: Exception) {
            Logger.e(TAG, e.toString())
            null
        }

    fun parseTime(date: Date): String? =
        try {
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(date)
        } catch (e: Exception) {
            Logger.e(TAG, e.toString())
            null
        }
}