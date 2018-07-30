package com.disanumber.timer.ui.interfaces

import android.content.Context

interface LoginView{
    fun showToast(message: String)
    fun authSuccesful()
    fun sendToMain()
    fun getContext(): Context
    fun sendToRegisterScreen()
}