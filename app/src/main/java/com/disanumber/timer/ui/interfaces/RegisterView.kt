package com.disanumber.timer.ui.interfaces

import android.content.Context

interface RegisterView{
    fun showToast(message: String)
    fun sendToMain()
    fun getContext(): Context
    fun sendToLoginScreen()
}