package com.disanumber.timer.ui.main

import com.arellomobile.mvp.MvpView

interface MainView: MvpView {

    fun initUI()
    fun sendToTimer()
    fun sendToSettings()
    fun sendToIntro()
    fun showToast(message: String)

}