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


}