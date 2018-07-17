package com.disanumber.timer.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.disanumber.timer.database.AppRepository
import com.disanumber.timer.database.TimerEntity


class ViewModel(application: Application) : AndroidViewModel(application) {


    var timers: LiveData<List<TimerEntity>>? = null
    private val mRepository: AppRepository = AppRepository.getInstance(application.applicationContext)

    init {
        timers = mRepository.timers
    }

    fun addSampleData() {
        mRepository.addSampleData()

    }
}