package com.disanumber.timer.ui.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.disanumber.timer.R
import com.disanumber.timer.broadcast.TimerExpiredReceiver
import com.disanumber.timer.util.NotificationUtil
import com.disanumber.timer.util.PrefUtil
import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.android.synthetic.main.content_timer.*
import java.util.*

class TimerActivity : AppCompatActivity() {

    companion object {
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemainig: Long): Long {//set alarm
            val wakeUpTime = (nowSeconds + secondsRemainig) * 1000//when wakeup time
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager//create alarm manger from context
            val intent = Intent(context, TimerExpiredReceiver::class.java)//intent with broadcast receiver
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context) {//remove alarm from background
            val intent = Intent(context, TimerExpiredReceiver::class.java)//intent with broadcast receiver
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager//create alarm manger from context
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)//0 is default for alarm not set
        }

        val nowSeconds: Long//get nowseconds from calendar
            get() = Calendar.getInstance().timeInMillis / 1000
    }


    enum class TimerState { //enum class for clasffieds state of timer
        Stopped,
        Paused,
        Running
    }

    private var timer: CountDownTimer? = null//object of CountDownTimer
    private var timerLengthSeconds = 0L//Timer Length
    private var timerState = TimerState.Stopped//timerState by default stopped
    private var secondsRemaining = 0L
    private var timerMax = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Timer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab_start.setOnClickListener({
            startTimer()
            timerState = TimerState.Running
            updateButtons()
        }
        )
        fab_pause.setOnClickListener({
            timer!!.cancel()
            timerState = TimerState.Paused
            updateButtons()
        }
        )
        fab_stop.setOnClickListener({
            if (timer != null) {
                timer!!.cancel()
            }
            onTimerFinished()
        }
        )


    }

    override fun onResume() {
        super.onResume()
        initTimer()
        removeAlarm(this)
        NotificationUtil.hideTimerNotification(this)
    }

    override fun onPause() {
        super.onPause()
        if (timerState == TimerState.Running) {
            timer!!.cancel()
            val wakeUpTime = setAlarm(this, nowSeconds, secondsRemaining)
            NotificationUtil.showTimerRunning(this, wakeUpTime, PrefUtil.getPrevTimerTitle(applicationContext))
        } else if (timerState == TimerState.Paused) {
            NotificationUtil.showTimerPaused(this, PrefUtil.getPrevTimerTitle(applicationContext))
        }
        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this)//save current time length
        PrefUtil.setSecondsRemaining(secondsRemaining, this)//save seconds remaining
        PrefUtil.setTimerState(timerState, this)//set timerState

    }

    private fun initTimer() {
        timerState = PrefUtil.getTimerState(this)//get timerstate

        if (timerState == TimerState.Stopped) {
            setNewTimerLength()

        } else {
            setPreviousTimerLength()
            txt_view_title.text = PrefUtil.getPrevTimerTitle(applicationContext)
        }

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(this)
        else
            timerLengthSeconds//if stopped seconds remianing will be full timer length seconds


        val alarmSetTime = PrefUtil.getAlarmSetTime(this)
        if (alarmSetTime > 0) //if alarm is set
            secondsRemaining -= nowSeconds - alarmSetTime//get secondsRemaining by minus now seconds and alarm set time

        if (secondsRemaining <= 0) {
            onTimerFinished()
        } else if (timerState == TimerState.Running)
            startTimer()
        updateButtons()
        updateCountDownUi()
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped
        setNewTimerLength()
        progress_countdown.progress = 0
        //if we stop timer we put in shared preferences full time
        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)//set full time
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountDownUi()
    }

    private fun startTimer() {
        timerState = TimerState.Running
        //init variable timer with object of CountDownTimer, override in this object to fun, when finished and on Tick
        //when one time tick timer
        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountDownUi()
            }
        }.start()
    }

    private fun setNewTimerLength() {
        txt_view_title.text = PrefUtil.getTimerTitle(applicationContext)

        val lengthInMinutes = PrefUtil.getTimerLength(this)//get timerlength in minutes
        PrefUtil.setPrevTimerTitle(applicationContext, PrefUtil.getTimerTitle(applicationContext))
        timerLengthSeconds = (lengthInMinutes * 60L)
        timerMax = PrefUtil.getTimerLength(this).toLong() * 60
        progress_countdown.max = timerLengthSeconds.toInt()//set max of progress


    }

    private fun setPreviousTimerLength() {
        timerLengthSeconds = PrefUtil.getPreviousTimerLenghtSeconds(this)
        progress_countdown.max = timerLengthSeconds.toInt()//set max of progress

    }

    private fun updateCountDownUi() {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinutesUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinutesUntilFinished.toString()
        if (minutesUntilFinished >= 60) {
            val hoursUntilfinished = minutesUntilFinished / 60
            val minutesInHoursUntilFinished = minutesUntilFinished - hoursUntilfinished * 60
            val minutesStr = minutesInHoursUntilFinished.toString()
            txt_view_countdown.text = "$hoursUntilfinished:${if (minutesStr.length == 2) minutesStr
            else "0" + minutesStr}:${if (secondsStr.length == 2) secondsStr
            else "0" + secondsStr}"
        } else {
            txt_view_countdown.text = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr
            else "0" + secondsStr}"
        }


        progress_countdown.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun updateButtons() {
        when (timerState) {
            TimerState.Running -> {
                fab_start.isEnabled = false
                fab_pause.isEnabled = true
                fab_stop.isEnabled = true
            }
            TimerState.Paused -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = true
            }
            TimerState.Stopped -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = false
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
