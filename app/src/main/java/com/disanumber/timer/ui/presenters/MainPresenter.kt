package com.disanumber.timer.ui.presenters

import android.content.Context
import com.disanumber.timer.R
import com.disanumber.timer.ui.interfaces.MainView
import com.disanumber.timer.util.PrefUtil

class MainPresenter(private var mainView: MainView) {

    fun initUI() {
        mainView.initUI()
    }


    fun checkTimerState(context: Context) {
        if (PrefUtil.getTimerLength(context) == 0) {
            mainView.showToast(context.getString(R.string.toast_choose_timer))

        } else mainView.sendToTimer()
    }

    fun sendToSettingsScreen() {
        mainView.sendToSettings()
    }


}