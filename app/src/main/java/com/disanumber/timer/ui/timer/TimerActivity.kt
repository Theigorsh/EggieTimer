package com.disanumber.timer.ui.timer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.disanumber.timer.R
import com.disanumber.timer.broadcast.TimerExpiredReceiver
import com.disanumber.timer.ui.activities.TimerExpiredActivity
import com.disanumber.timer.util.NotificationUtil
import com.disanumber.timer.util.PrefUtil
import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.android.synthetic.main.content_timer.*
import java.util.*


class TimerActivity : MvpAppCompatActivity(), TimerView {

    lateinit var prefs: PrefUtil

    companion object {

        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long {
            var prefs: PrefUtil = PrefUtil(context)
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000//when wakeup time
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager//create alarm manger from context
            val intent = Intent(context, TimerExpiredReceiver::class.java)//intent with broadcast receiver
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            prefs.setAlarmSetTime(nowSeconds)
            return wakeUpTime
        }

        fun removeAlarm(context: Context) {
            var prefs: PrefUtil = PrefUtil(context) //remove alarm from background
            val intent = Intent(context, TimerExpiredReceiver::class.java)//intent with broadcast receiver
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager//create alarm manger from context
            alarmManager.cancel(pendingIntent)
            prefs.setAlarmSetTime(0)//0 is default for alarm not set
        }

        val nowSeconds: Long
            //get nowseconds from calendar
            get() = Calendar.getInstance().timeInMillis / 1000
    }


    private var timer: CountDownTimer? = null//object of CountDownTimer


    @InjectPresenter
    lateinit var presenter: TimerPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.timer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        prefs = PrefUtil(this)
        fab_start.setOnClickListener {
            presenter.onStartButtonClicked()
        }

        fab_pause.setOnClickListener {
            presenter.onPauseButtonClicked()
        }

        fab_stop.setOnClickListener {
            presenter.onStopButtonClicked()
            presenter.finishTimer()
        }


    }


    override fun cancelTimer() {
        if (timer != null) {
            timer!!.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.fromList = intent.getBooleanExtra("from_list", false)
        initTimer()
        removeAlarm(this)
        NotificationUtil.hideTimerNotification(this)
    }

    override fun onPause() {
        super.onPause()
        if (presenter.timerState == TimerState.Running) {
            timer!!.cancel()
            val wakeUpTime = setAlarm(this, nowSeconds, presenter.secondsRemaining)
            NotificationUtil.showTimerRunning(this, wakeUpTime, prefs.getPrevTimerTitle())
        } else if (presenter.timerState == TimerState.Paused) {
            NotificationUtil.showTimerPaused(this, prefs.getPrevTimerTitle())
        }
        prefs.setPreviousTimerLengthSeconds(presenter.timerLengthSeconds)//save current time length
        prefs.setSecondsRemaining(presenter.secondsRemaining)//save seconds remaining
        prefs.setTimerState(presenter.timerState)//set timerState

    }

    private fun initTimer() {
        presenter.timerState = prefs.getTimerState()//get timerstate

        if (presenter.timerState == TimerState.Stopped) {
            setNewTimerLength()
        } else {
            if (presenter.fromList!!) {
                createAlertDialog()
                intent.removeExtra("from_list")
            }
            setPreviousTimerLength()
            txt_view_title.text = prefs.getPrevTimerTitle()
        }

        presenter.secondsRemaining = if (presenter.timerState == TimerState.Running || presenter.timerState == TimerState.Paused)
            prefs.getSecondsRemaining()
        else
            presenter.timerLengthSeconds//if stopped seconds remianing will be full timer length seconds


        val alarmSetTime = prefs.getAlarmSetTime()
        if (alarmSetTime > 0) //if alarm is set
            presenter.secondsRemaining -= nowSeconds - alarmSetTime//get secondsRemaining by minus now seconds and alarm set time

        if (presenter.secondsRemaining <= 0) {
            onTimerFinished(false)
        } else if (presenter.timerState == TimerState.Running)
            startTimer()
        updateButtons()
        updateCountDownUi()


    }

    override fun onTimerFinished(isSelfExpired: Boolean) {
        presenter.timerState = TimerState.Stopped
        setNewTimerLength()
        progress_countdown.progress = 0

        //if we stop timer we put in shared preferences full time
        prefs.setSecondsRemaining(presenter.timerLengthSeconds)//set full time
        presenter.secondsRemaining = presenter.timerLengthSeconds

        updateButtons()
        updateCountDownUi()
        if (isSelfExpired) {
            val intentExpired = Intent()
            intentExpired.setClassName(packageName, TimerExpiredActivity::class.java.name)
            intentExpired.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intentExpired)
        }

    }

    override fun startTimer() {
        presenter.timerState = TimerState.Running
        //init variable timer with object of CountDownTimer, override in this object to fun, when finished and on Tick
        //when one time tick timer
        timer = object : CountDownTimer(presenter.secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished(true)

            override fun onTick(millisUntilFinished: Long) {
                presenter.secondsRemaining = millisUntilFinished / 1000
                updateCountDownUi()
            }
        }.start()
    }

    private fun setNewTimerLength() {
        txt_view_title.text = prefs.getTimerTitle()

        val lengthInMinutes = prefs.getTimerLength()//get timerlength in minutes
        prefs.setPrevTimerTitle(prefs.getTimerTitle())
        prefs.setPrevTimerImage(prefs.getTimerImage())
        presenter.timerLengthSeconds = (lengthInMinutes * 60L)
        presenter.timerMax = prefs.getTimerLength().toLong() * 60
        progress_countdown.max = presenter.timerLengthSeconds.toInt()//set max of progress


    }

    private fun setPreviousTimerLength() {
        presenter.timerLengthSeconds = prefs.getPreviousTimerLengthSeconds()
        progress_countdown.max = presenter.timerLengthSeconds.toInt()//set max of progress

    }

    private fun updateCountDownUi() {
        val minutesUntilFinished = presenter.secondsRemaining / 60
        val secondsInMinutesUntilFinished = presenter.secondsRemaining - minutesUntilFinished * 60
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

        progress_countdown.progress = (presenter.timerLengthSeconds - presenter.secondsRemaining).toInt()

    }

    override fun updateButtons() {
        when (presenter.timerState) {
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

    private fun createAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.set_timer))
                .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                    if (timer != null) {
                        timer!!.cancel()
                    }
                    onTimerFinished(false)
                }
                .setNegativeButton(getString(R.string.no)) { dialog, id ->
                    dialog.dismiss()
                }
                .setIcon(R.mipmap.ic_launcher)
        // Create the AlertDialog object and return it

        val alert: AlertDialog = builder.create()
        alert.show()

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
