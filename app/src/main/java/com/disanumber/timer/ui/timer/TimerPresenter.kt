package com.disanumber.timer.ui.timer

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.disanumber.timer.util.PrefUtil

@InjectViewState
class TimerPresenter: MvpPresenter<TimerView>() {

    var timerLengthSeconds = 0L//Timer Length
    var timerState = TimerState.Stopped//timerState by default stopped
    var secondsRemaining = 0L
    var timerMax = 0L
    var fromList: Boolean? = null
    lateinit var prefs: PrefUtil


    fun onStartButtonClicked() {
        viewState.startTimer()
        timerState = TimerState.Running
        viewState.updateButtons()
    }

    fun onPauseButtonClicked() {
        viewState.cancelTimer()
        timerState = TimerState.Paused
        viewState.updateButtons()
    }

    fun onStopButtonClicked() {
        viewState.cancelTimer()
    }

    fun finishTimer(){
        viewState.onTimerFinished(false)
    }

    fun updateCountDownUi() {
        val countDownText: String

        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinutesUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinutesUntilFinished.toString()
        if (minutesUntilFinished >= 60) {
            val hoursUntilfinished = minutesUntilFinished / 60
            val minutesInHoursUntilFinished = minutesUntilFinished - hoursUntilfinished * 60
            val minutesStr = minutesInHoursUntilFinished.toString()
            countDownText= "$hoursUntilfinished:${if (minutesStr.length == 2) minutesStr
            else "0" + minutesStr}:${if (secondsStr.length == 2) secondsStr
            else "0" + secondsStr}"
        } else {
            countDownText = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr
            else "0" + secondsStr}"
        }

        val progress = (timerLengthSeconds - secondsRemaining).toInt()
        viewState.updateCountDownUi(countDownText, progress)

    }

    fun setNewTimerLength() {
        val timerTitle = prefs.getTimerTitle()
        val lengthInMinutes = prefs.getTimerLength()
        prefs.setPrevTimerTitle(prefs.getTimerTitle())
        prefs.setPrevTimerImage(prefs.getTimerImage())
        timerLengthSeconds = (lengthInMinutes * 60L)
        timerMax = prefs.getTimerLength().toLong() * 60
        viewState.setNewTimerLength(timerTitle, secondsRemaining.toInt())
    }

    fun onPause() {
        if (timerState == TimerState.Running) {
            viewState.cancelTimer()
            viewState.showTimerRunning(secondsRemaining, prefs.getPrevTimerTitle())
        } else if (timerState == TimerState.Paused) {
            viewState.showTimerPaused(prefs.getPrevTimerTitle())
        }
        prefs.setPreviousTimerLengthSeconds(timerLengthSeconds)//save current time length
        prefs.setSecondsRemaining(secondsRemaining)//save seconds remaining
        prefs.setTimerState(timerState)//set timerState
    }

    fun initTimer() {
        timerState = prefs.getTimerState()

        if (timerState == TimerState.Stopped) {
            setNewTimerLength()
        } else {
            if (fromList!!) {
                viewState.createAlertDialog()
            }
            timerLengthSeconds = prefs.getPreviousTimerLengthSeconds()
            viewState.setPreviousTimerLength(prefs.getPrevTimerTitle())
        }

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            prefs.getSecondsRemaining()
        else
            timerLengthSeconds//if stopped seconds remianing will be full timer length seconds

        val alarmSetTime = prefs.getAlarmSetTime()
        if (alarmSetTime > 0) //if alarm is set
            secondsRemaining -= TimerActivity.nowSeconds - alarmSetTime//get secondsRemaining by minus now seconds and alarm set time

        if (secondsRemaining <= 0) {
            viewState.onTimerFinished(false)
        } else if (timerState == TimerState.Running)
            viewState.startTimer()
        viewState.updateButtons()
        updateCountDownUi()
    }


}