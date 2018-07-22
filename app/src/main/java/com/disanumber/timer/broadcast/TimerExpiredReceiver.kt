package com.disanumber.timer.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.disanumber.timer.activities.TimerActivity
import com.disanumber.timer.util.NotificationUtil
import com.disanumber.timer.util.PrefUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        NotificationUtil.showTimerExpired(context)
        PrefUtil.setTimerState(TimerActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0,context)

    }
}
