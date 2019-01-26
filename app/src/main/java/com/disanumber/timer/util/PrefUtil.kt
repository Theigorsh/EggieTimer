package com.disanumber.timer.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.disanumber.timer.ui.timer.TimerState

class PrefUtil(context: Context) {

    var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val TIMER_DATA_ID = "com.disanumber.timer.timer_data"

    fun getData(): Boolean {
        return preferences.getBoolean(TIMER_DATA_ID, false)
    }

    fun setDataAdded() {
        val editor = preferences.edit()
        editor.putBoolean(TIMER_DATA_ID, true)
        editor.apply()
    }


    private val TIMER_LENGTH_ID = "com.disanumber.timer.timer_length"
    private val TIMER_TITLE_ID = "com.disanumber.timer.timer_title"
    private val TIMER_IMAGE_ID = "com.disanumber.timer.timer_image"

    fun getTimerLength(): Int {
        return preferences.getInt(TIMER_LENGTH_ID, 0)
    }

    fun getTimerTitle(): String {
        return preferences.getString(TIMER_TITLE_ID, "Choose timer to use")!!
    }

    fun getTimerImage(): String {
        return preferences.getString(TIMER_IMAGE_ID, "image")!!
    }

    private val TIMER_PREV_TITLE_ID = "com.disanumber.timer.timer_notif_title"

    fun setPrevTimerTitle(title: String) {
        val editor = preferences.edit()
        editor.putString(TIMER_PREV_TITLE_ID, title)
        editor.apply()
    }

    fun getPrevTimerTitle(): String {
        return preferences.getString(TIMER_PREV_TITLE_ID, "Choose timer to use")!!
    }

    private val TIMER_PREV_IMAGE_ID = "com.disanumber.timer.timer_prev_image"

    fun setPrevTimerImage(image: String) {
        val editor = preferences.edit()
        editor.putString(TIMER_PREV_IMAGE_ID, image)
        editor.apply()
    }

    fun getPrevTimerImage(): String {
        return preferences.getString(TIMER_PREV_IMAGE_ID, "image")!!
    }


    fun setTimerLength(length: Int, title: String, image: String) {
        val editor = preferences.edit()
        editor.putInt(TIMER_LENGTH_ID, length)
        editor.putString(TIMER_TITLE_ID, title)
        editor.putString(TIMER_IMAGE_ID, image)
        editor.apply()
    }

    private val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.disanumber.timer.previous_timer_length"

    fun getPreviousTimerLengthSeconds(): Long {
        return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
    }

    fun setPreviousTimerLengthSeconds(seconds: Long) {
        val editor = preferences.edit()
        editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
        editor.apply()
    }

    private val TIMER_STATE_ID = "com.disanumber.timer.timer_state"

    fun getTimerState(): TimerState {
        val ordinal = preferences.getInt(TIMER_STATE_ID, 0)
        return TimerState.values()[ordinal]
    }

    fun setTimerState(state: TimerState) {
        val editor = preferences.edit()
        val ordinal = state.ordinal
        editor.putInt(TIMER_STATE_ID, ordinal)
        editor.apply()
    }

    private val SECONDS_REMAINING_ID = "com.disanumber.timer.previous_timer_length"

    fun getSecondsRemaining(): Long {
        return preferences.getLong(SECONDS_REMAINING_ID, 0)
    }

    fun setSecondsRemaining(seconds: Long) {
        val editor = preferences.edit()
        editor.putLong(SECONDS_REMAINING_ID, seconds)
        editor.apply()
    }

    private val AlARM_SET_TIME_ID = "com.disanumber.timer.background_time"
    fun getAlarmSetTime(): Long {
        return preferences.getLong(AlARM_SET_TIME_ID, 0)
    }

    fun setAlarmSetTime(time: Long) {
        val editor = preferences.edit()
        editor.putLong(AlARM_SET_TIME_ID, time)
        editor.apply()

    }
}