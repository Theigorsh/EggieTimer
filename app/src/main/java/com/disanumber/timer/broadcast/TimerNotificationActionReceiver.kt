package com.disanumber.timer.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.disanumber.timer.ui.timer.TimerActivity
import com.disanumber.timer.ui.timer.TimerState
import com.disanumber.timer.util.Constants
import com.disanumber.timer.util.NotificationUtil
import com.disanumber.timer.util.PrefUtil

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val prefs = PrefUtil(context)
        when (intent.action) {
            Constants.ACTION_STOP -> {
                TimerActivity.removeAlarm(context)
                prefs.setTimerState(TimerState.Stopped)
                NotificationUtil.hideTimerNotification(context)
            }
            Constants.ACTION_PAUSE -> {
                var secondsRemaining = prefs.getSecondsRemaining()
                val alarmSetTime = prefs.getAlarmSetTime()
                val nowSeconds = TimerActivity.nowSeconds
                secondsRemaining -= nowSeconds - alarmSetTime//time in which timer running in background
                prefs.setSecondsRemaining(secondsRemaining)
                TimerActivity.removeAlarm(context)
                prefs.setTimerState(TimerState.Paused)
                NotificationUtil.showTimerPaused(context, prefs.getPrevTimerTitle())
            }
            Constants.ACTION_RESUME -> {
                val secondsRemaining = prefs.getSecondsRemaining()
                val wakeUpTime = TimerActivity.setAlarm(context, TimerActivity.nowSeconds, secondsRemaining)
                prefs.setTimerState(TimerState.Running)
                NotificationUtil.showTimerRunning(context, wakeUpTime, prefs.getPrevTimerTitle())
            }
            Constants.ACTION_START -> {
                val minutesRemaining = prefs.getTimerLength()
                val secondsRemaining = minutesRemaining * 60L
                val wakeUpTime = TimerActivity.setAlarm(context, TimerActivity.nowSeconds, secondsRemaining)
                prefs.setTimerState(TimerState.Running)
                prefs.setSecondsRemaining(secondsRemaining)
                NotificationUtil.showTimerRunning(context, wakeUpTime, prefs.getPrevTimerTitle())
            }
        }
    }
}
