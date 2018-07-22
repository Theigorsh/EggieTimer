package com.disanumber.timer.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "timers")
class TimerEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var image: Int? = null
    var length: Int? = null
    var step: Int? = null
    var min: Int? = null
    var max: Int? = null
    var type: Int? = null

    @Ignore
    constructor() {
    }

    constructor(id: Int, name: String?, image: Int?, length: Int?, step: Int?, min: Int?, max: Int?, type: Int?) {
        this.id = id
        this.name = name
        this.image = image
        this.length = length
        this.step = step
        this.min = min
        this.max = max
        this.type = type
    }
    @Ignore
    constructor(name: String?, image: Int?, length: Int?, step: Int?, min: Int?, max: Int?, type: Int?) {
        this.name = name
        this.image = image
        this.length = length
        this.step = step
        this.min = min
        this.max = max
        this.type = type
    }


}
