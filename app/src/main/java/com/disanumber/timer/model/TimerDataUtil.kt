package com.disanumber.timer.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.disanumber.timer.database.TimerEntity

class TimerDataUtil {

    companion object {
        fun getTimers(): List<TimerEntity> {
            val timers = ArrayList<TimerEntity>()
            timers.add(TimerEntity("Sport", "Sport Timer", "puppy1", 8, 5, 10, 0))
            timers.add(TimerEntity("Sport", "Sport Timer", "puppy1", 8, 5, 10, 0))
            timers.add(TimerEntity("Sport", "Sport Timer", "puppy1", 8, 5, 10, 0))
            timers.add(TimerEntity("Food", "Food Timer", "puppy1", 7, 5, 10, 1))
            timers.add(TimerEntity("Food", "Food Timer", "puppy1", 7, 5, 10, 1))
            timers.add(TimerEntity("Food", "Food Timer", "puppy1", 7, 5, 10, 1))
            timers.add(TimerEntity("Health", "Health Timer", "puppy1", 7, 5, 10, 2))
            timers.add(TimerEntity("Health", "Health Timer", "puppy1", 7, 5, 10, 2))
            timers.add(TimerEntity("Health", "Health Timer", "puppy1", 7, 5, 10, 2))
            return timers
        }

        fun getDrawableByName(name: String, context: Context): Drawable {
            val resources = context.resources
            val resourceId = resources.getIdentifier(name, "drawable",
                    context.packageName)
            return ContextCompat.getDrawable(context, resourceId)!!
        }
    }

}