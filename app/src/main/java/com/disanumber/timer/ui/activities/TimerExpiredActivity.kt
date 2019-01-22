package com.disanumber.timer.ui.activities

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.disanumber.timer.R
import com.disanumber.timer.ui.timer.TimerActivity
import com.disanumber.timer.ui.timer.TimerState
import com.disanumber.timer.util.NotificationUtil
import com.disanumber.timer.util.PrefUtil
import com.disanumber.timer.util.TimerDataUtil
import kotlinx.android.synthetic.main.activity_timer_expired.*


class TimerExpiredActivity : AppCompatActivity() {
    private var mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_expired)
        this.setFinishOnTouchOutside(false)
        val prefs = PrefUtil(this)
        exp_timer_title.text = prefs.getPrevTimerTitle()
        exp_timer_image.setImageDrawable(TimerDataUtil.getDrawableByName(prefs.getPrevTimerImage(), this))
        btn_start.setOnClickListener {
            val minutesRemaining = prefs.getTimerLength()
            val secondsRemaining = minutesRemaining * 60L
            val wakeUpTime = TimerActivity.setAlarm(this, TimerActivity.nowSeconds, secondsRemaining)
            prefs.setTimerState(TimerState.Running)
            prefs.setSecondsRemaining(secondsRemaining)
            NotificationUtil.showTimerRunning(this, wakeUpTime, prefs.getPrevTimerTitle())


            mp!!.stop()
            mp!!.release()
            mp =null

            finish()

        }

        exp_close.setOnClickListener {

            mp!!.stop()
            mp!!.release()
            mp =null
            finish()


        }
    }

    override fun onResume() {
        super.onResume()
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val path = preferences.getString("ringtone_sound", "")
        if(mp ==null){

        if (!path.isEmpty()) {
            try{
            startSound(path)}
            catch (e: NullPointerException){
                startSoundFromUri(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))

            }
        } else {
            try {
                startSoundFromUri(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            } catch (e: NullPointerException) {
                startSoundFromUri(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            }
        }
        }
    }

    private fun startSound(path: String) {
        mp = MediaPlayer.create(applicationContext, Uri.parse(path))
        mp!!.start()

    }

    private fun startSoundFromUri(uri: Uri) {
        mp = MediaPlayer.create(applicationContext, uri)
        mp!!.start()
    }

    override fun onStart() {
        super.onStart()
        this.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    }

}
