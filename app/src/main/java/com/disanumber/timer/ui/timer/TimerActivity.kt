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

        fun setAlarm(prefs: PrefUtil, context: Context, nowSeconds: Long, secondsRemaining: Long): Long {
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000//when wakeup time
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager//create alarm manger from context
            val intent = Intent(context, TimerExpiredReceiver::class.java)//intent with broadcast receiver
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            prefs.setAlarmSetTime(nowSeconds)
            return wakeUpTime
        }

        fun removeAlarm(prefs: PrefUtil, context: Context) {
            val intent = Intent(context, TimerExpiredReceiver::class.java)//intent with broadcast receiver
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager//create alarm manger from context
            alarmManager.cancel(pendingIntent)
            prefs.setAlarmSetTime(0)//0 is default for alarm not set
        }

        val nowSeconds: Long
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
        presenter.prefs = prefs
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
        presenter.initTimer()
        removeAlarm(prefs, this)
        NotificationUtil.hideTimerNotification(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }


    override fun showTimerRunning(secondsRemaining: Long, prevTimerTitle: String) {
        val wakeUpTime = setAlarm(prefs, this, nowSeconds, secondsRemaining)
        NotificationUtil.showTimerRunning(this, wakeUpTime, prevTimerTitle)
    }

    override fun showTimerPaused(prevTimerTitle: String) {
        NotificationUtil.showTimerPaused(this, prevTimerTitle)

    }

    override fun onTimerFinished(isSelfExpired: Boolean) {
        presenter.timerState = TimerState.Stopped
        presenter.setNewTimerLength()
        progress_countdown.progress = 0

        //if we stop timer we put in shared preferences full time
        prefs.setSecondsRemaining(presenter.timerLengthSeconds)//set full time
        presenter.secondsRemaining = presenter.timerLengthSeconds

        updateButtons()
        presenter.updateCountDownUi()

        if (isSelfExpired) {
            val intentExpired = Intent()
            intentExpired.setClassName(packageName, TimerExpiredActivity::class.java.name)
            intentExpired.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intentExpired)
        }

    }

    override fun startTimer() {
        presenter.timerState = TimerState.Running
        timer = object : CountDownTimer(presenter.secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished(true)

            override fun onTick(millisUntilFinished: Long) {
                presenter.secondsRemaining = millisUntilFinished / 1000
                presenter.updateCountDownUi()
            }
        }.start()
    }

    override fun setNewTimerLength(timerTitle: String, progressMax: Int) {
        txt_view_title.text = timerTitle
        progress_countdown.max = progressMax


    }

    override fun setPreviousTimerLength(prevTimerTitle: String) {
        progress_countdown.max = presenter.timerLengthSeconds.toInt()//set max of progress
        txt_view_title.text = prevTimerTitle

    }

    override fun updateCountDownUi(countDownText: String, progress: Int) {
        txt_view_countdown.text = countDownText
        progress_countdown.progress = progress

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

    override fun createAlertDialog() {
        intent.removeExtra("from_list")
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.set_timer))
                .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                    cancelTimer()
                    onTimerFinished(false)
                }
                .setNegativeButton(getString(R.string.no)) { dialog, id ->
                    dialog.dismiss()
                }
                .setIcon(R.mipmap.ic_launcher)
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
