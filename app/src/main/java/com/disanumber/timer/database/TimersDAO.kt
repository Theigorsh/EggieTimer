package com.disanumber.timer.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.disanumber.timer.model.TimerEntity

@Dao
interface TimersDao {

    @Query("SELECT * FROM timers ORDER BY name")
    fun getAll(): LiveData<List<TimerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTimer(timerEntity: TimerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(timers: List<TimerEntity>)

    @Query("DELETE FROM timers WHERE id = :id")
    fun deleteTimer(id: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(timer: TimerEntity): Int

}
