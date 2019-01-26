package com.disanumber.timer.ui.timer

import com.arellomobile.mvp.MvpView

interface TimerView: MvpView {

    fun startTimer()
    fun updateButtons()
    fun cancelTimer()
    fun onTimerFinished(isSelfExpired: Boolean)
    fun updateCountDownUi(countDownText: String, progress: Int)
    fun setNewTimerLength(timerTitle: String, progressMax: Int)
    fun showTimerRunning(secondsRemaining: Long, prevTimerTitle: String)
    fun showTimerPaused(prevTimerTitle: String)
    fun createAlertDialog()
    fun setPreviousTimerLength(prevTimerTitle: String)
}