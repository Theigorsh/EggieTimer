package com.disanumber.timer.ui.main

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.disanumber.timer.R
import com.disanumber.timer.util.PrefUtil

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    lateinit var prefs: PrefUtil

    fun initUI() {
        viewState.initUI()
    }

    fun checkTimerState(context: Context) {
        if (prefs.getTimerLength() == 0) {
            viewState.showToast(context.getString(R.string.toast_choose_timer))

        } else viewState.sendToTimer()
    }

    fun sendToSettingsScreen() {
        viewState.sendToSettings()
    }

}