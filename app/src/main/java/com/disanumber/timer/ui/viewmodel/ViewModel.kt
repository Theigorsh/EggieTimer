package com.disanumber.timer.ui.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.disanumber.timer.database.AppRepository
import com.disanumber.timer.model.TimerEntity


class ViewModel(application: Application) : AndroidViewModel(application) {


    var timers: LiveData<List<TimerEntity>>? = null
    private val mRepository: AppRepository = AppRepository.getInstance(application.applicationContext)

    init {
        timers = mRepository.timers
    }

    fun addData() {
        mRepository.addTimerData()

    }
    fun update(timer: TimerEntity){
        mRepository.update(timer)
    }
    fun addTimer(timer: TimerEntity){
        mRepository.insertTimer(timer)
    }

    fun addPremiumData() {
        mRepository.addPremiumData()
    }
    fun deleteTimer(id: Int) {
        mRepository.deleteTimer(id)
    }

}