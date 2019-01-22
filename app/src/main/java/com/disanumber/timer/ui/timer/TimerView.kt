package com.disanumber.timer.ui.timer

import com.arellomobile.mvp.MvpView

interface TimerView: MvpView {

    fun startTimer()
    fun updateButtons()
    fun cancelTimer()
    fun onTimerFinished(isSelfExpired: Boolean)
}