package com.disanumber.timer.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface TimersDao {

    @Query("SELECT * FROM timers ORDER BY name DESC")
    fun getAllByType(): LiveData<List<TimerEntity>>

    @get:Query("SELECT COUNT(*) FROM timers")
    val count: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTimer(timerEntity: TimerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(timers: List<TimerEntity>)

    @Delete
    fun deleteTimer(timerEntity: TimerEntity)

    @Query("SELECT * FROM timers WHERE id = :id")
    fun getTimerById(id: Int): TimerEntity

    @Query("DELETE FROM timers")
    fun deleteAll(): Int

}
