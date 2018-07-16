package com.disanumber.timer.util

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.disanumber.timer.R
import com.disanumber.timer.timer.TimerActivity
import com.disanumber.timer.broadcast.TimerNotificationActionReceiver
import java.text.SimpleDateFormat
import java.util.*

class NotificationUtil {
    companion object {
        //FOR API 26 and more
        private const val CHANNNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "Timer App Timer"
        private const val TIMER_ID = 0


        fun showTimerExpired(context: Context) {
            val startIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            startIntent.action = Constants.ACTION_START
            val startPendingIntent = PendingIntent.getBroadcast(context,//create pending intent
                    0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val nBuilder = getBasicNotificationBuilder(context, CHANNNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer Expired!")
                    .setContentText("Start again?")
                    .setContentIntent(getPendingIntentWitStack(context, TimerActivity::class.java))
                    .addAction(R.drawable.ic_start, "Start",startPendingIntent)
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)
            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun showTimerRunning(context: Context, wakeUpTime: Long){
            val stopIntent = Intent(context, TimerNotificationActionReceiver::class.java)//stopIntent
            stopIntent.action = Constants.ACTION_STOP
            val stopPendingIntent = PendingIntent.getBroadcast(context,
                    0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val pauseIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            pauseIntent.action = Constants.ACTION_PAUSE
            val pausePendingIntent = PendingIntent.getBroadcast(context,
                    0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val df = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)//dateformat current date

            val nBuilder = getBasicNotificationBuilder(context, CHANNNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer is Running.")
                    .setContentText("End: ${df.format(Date(wakeUpTime))}")
                    .setContentIntent(getPendingIntentWitStack(context, TimerActivity::class.java))
                    .setOngoing(true)
                    .addAction(R.drawable.ic_stop, "Stop", stopPendingIntent)
                    .addAction(R.drawable.ic_pause, "Pause", pausePendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }
        fun showTimerPaused(context: Context){
            val resumeIntent = Intent(context, TimerNotificationActionReceiver::class.java)//intetn to resumr
            resumeIntent.action = Constants.ACTION_RESUME
            val resumePendingIntent = PendingIntent.getBroadcast(context,
                    0, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val nBuilder = getBasicNotificationBuilder(context, CHANNNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer is paused.")
                    .setContentText("Resume?")
                    .setContentIntent(getPendingIntentWitStack(context, TimerActivity::class.java))
                    .setOngoing(true)
                    .addAction(R.drawable.ic_start, "Resume", resumePendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }
        //func to create to notification
        private fun getBasicNotificationBuilder(context: Context, channnelID: String, playSound: Boolean)//func that return notification
                : NotificationCompat.Builder {
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)//get sound of notif
            val nBuilder = NotificationCompat.Builder(context, channnelID)
                    .setSmallIcon(R.drawable.ic_timer)
                    .setAutoCancel(true)
            if (playSound) nBuilder.setSound(notificationSound)
            return nBuilder

        }
        fun hideTimerNotification(context: Context){
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.cancel(TIMER_ID)
        }
        private fun <T> getPendingIntentWitStack(context: Context, javaClass: Class<T>): PendingIntent {
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP//stack of activities

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)//activity we want to open
            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        //Extension Func
        @TargetApi(26)//target func for api
        private fun NotificationManager.createNotificationChannel(channnelID: String,
                                                                  channelName: String,
                                                                  playSound: Boolean){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){//if API 26 or higher
                val channelImportance = if(playSound) NotificationManager.IMPORTANCE_DEFAULT
                else NotificationManager.IMPORTANCE_LOW//check importance level
                val nChannel = NotificationChannel(channnelID,channelName, channelImportance)//object of notif channel
                nChannel.lightColor = Color.BLUE
                this.createNotificationChannel(nChannel)//create notif channel
            }
        }
    }
}