package com.disanumber.timer.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import com.disanumber.timer.ui.fragments.IntroSlideFragment
import com.github.paolorotolo.appintro.AppIntro

class IntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSlide(IntroSlideFragment.newInstance(1))
        addSlide(IntroSlideFragment.newInstance(2))
        addSlide(IntroSlideFragment.newInstance(3))
        setSeparatorColor(Color.parseColor("#FFFFFF"))

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        sendToMain()

    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        sendToMain()
    }

    private fun sendToMain() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

}
