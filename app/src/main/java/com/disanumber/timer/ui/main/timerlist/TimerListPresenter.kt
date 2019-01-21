package com.disanumber.timer.ui.main.timerlist

import android.arch.lifecycle.LiveData
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.disanumber.timer.database.AppRepository
import com.disanumber.timer.model.TimerEntity

@InjectViewState
class TimerListPresenter : MvpPresenter<TimerListView>() {


    var timers: LiveData<List<TimerEntity>>? = null
    private lateinit var repository: AppRepository

    fun addData() {
        repository.addTimerData()

    }
    fun update(timer: TimerEntity){
        repository.update(timer)
    }
    fun addTimer(timer: TimerEntity){
        repository.insertTimer(timer)
    }

    fun deleteTimer(id: Int) {
        repository.deleteTimer(id)
    }

    fun setData(repository: AppRepository){
        this.repository = repository
        timers = repository.timers
        viewState.onDataLoaded()
    }

}