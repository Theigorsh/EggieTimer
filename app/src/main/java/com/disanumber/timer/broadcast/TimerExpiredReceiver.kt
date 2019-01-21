package com.disanumber.timer.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.disanumber.timer.ui.activities.TimerExpiredActivity
import com.disanumber.timer.ui.timer.TimerActivity
import com.disanumber.timer.util.NotificationUtil
import com.disanumber.timer.util.PrefUtil


class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        setTimerExpiredActions(context)

    }
    private fun setTimerExpiredActions(context: Context){
        NotificationUtil.hideTimerNotification(context)

        val intentExpired = Intent()
        intentExpired.setClassName(context.packageName, TimerExpiredActivity::class.java.name)
        intentExpired.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intentExpired)

        PrefUtil.setTimerState(TimerActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0,context)
    }
}
