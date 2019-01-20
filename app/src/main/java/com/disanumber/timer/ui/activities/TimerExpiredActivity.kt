package com.disanumber.timer.ui.activities

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.disanumber.timer.R
import com.disanumber.timer.util.NotificationUtil
import com.disanumber.timer.util.PrefUtil
import com.disanumber.timer.util.TimerDataUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_timer_expired.*


class TimerExpiredActivity : AppCompatActivity() {
    private var mp: MediaPlayer? = null
    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_expired)
        this.setFinishOnTouchOutside(false)
        MobileAds.initialize(this, getString(R.string.app_ads_id))
        interstitialAd = InterstitialAd(this)
        interstitialAd!!.adUnitId = getString(R.string.banner_fullscreen)
        if (!PrefUtil.getVersion(this)) {
            interstitialAd!!.loadAd(AdRequest.Builder().build())
        }
        exp_timer_title.text = PrefUtil.getPrevTimerTitle(this)
        exp_timer_image.setImageDrawable(TimerDataUtil.getDrawableByName(PrefUtil.getPrevTimerImage(this), this))
        btn_start.setOnClickListener {
            val minutesRemaining = PrefUtil.getTimerLength(this)
            val secondsRemaining = minutesRemaining * 60L
            val wakeUpTime = TimerActivity.setAlarm(this, TimerActivity.nowSeconds, secondsRemaining)
            PrefUtil.setTimerState(TimerActivity.TimerState.Running, this)
            PrefUtil.setSecondsRemaining(secondsRemaining, this)
            NotificationUtil.showTimerRunning(this, wakeUpTime, PrefUtil.getPrevTimerTitle(this))

            if (interstitialAd!!.isLoaded) {
                interstitialAd!!.show()
            }
            mp!!.stop()
            mp!!.release()
            mp =null

            finish()

        }

        exp_close.setOnClickListener {

            if (interstitialAd!!.isLoaded) {
                interstitialAd!!.show()
            }

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
