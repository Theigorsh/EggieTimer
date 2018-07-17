package com.disanumber.timer.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "timers")
class TimerEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var description: String? = null
    var image: String? = null
    var length: Int? = null
    var min: Int? = null
    var max: Int? = null
    var type: Int? = null

    @Ignore
    constructor() {
    }


    constructor(name: String?, description: String?, image: String?, length: Int?, min: Int?, max: Int?, type: Int?) {
        this.name = name
        this.description = description
        this.image = image
        this.length = length
        this.min = min
        this.max = max
        this.type = type
    }

    @Ignore
    constructor(id: Int, name: String?, description: String?, image: String?, length: Int?, min: Int?, max: Int?, type: Int?) {
        this.id = id
        this.name = name
        this.description = description
        this.image = image
        this.length = length
        this.min = min
        this.max = max
        this.type = type
    }


}
