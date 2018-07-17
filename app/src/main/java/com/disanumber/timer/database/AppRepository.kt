package com.disanumber.timer.database

import android.arch.lifecycle.LiveData
import android.content.Context
import com.disanumber.timer.model.TimerDataUtil
import java.util.concurrent.Executors


class AppRepository private constructor(context: Context) {

    var timers: LiveData<List<TimerEntity>>? = null
    private val mDb: AppDatabase = AppDatabase.getInstance(context)!!
    private val executor = Executors.newSingleThreadExecutor()


    private fun getAllNotes(): LiveData<List<TimerEntity>> {
        return mDb.timerDao().getAllByType()
    }


    init {
        timers = getAllNotes()
    }

    fun addSampleData() {
        executor.execute {
            mDb.timerDao().insertAll(TimerDataUtil.getTimers())
        }
    }

    companion object {
        private var ourInstance: AppRepository? = null

        fun getInstance(context: Context): AppRepository {
            if (ourInstance == null) {
                ourInstance = AppRepository(context)
            }
            return ourInstance as AppRepository
        }
    }
}

