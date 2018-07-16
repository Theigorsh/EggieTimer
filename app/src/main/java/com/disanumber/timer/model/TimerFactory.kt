package com.disanumber.timer.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.disanumber.timer.R
import kotlinx.android.synthetic.main.activity_timer.view.*
import java.sql.Time

class TimerFactory(private val context: Context) {


    val timers: ArrayList<Timer>
        get() {
            val result = ArrayList<Timer>()
            result.add(Timer("Food Timer", "Description", getDrawableByName("puppy1"), 5, false, true))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))
            result.add(Timer("Sport Timer", "Description", getDrawableByName("puppy1"), 10, true, false))

            result.add(Timer("Food Timer", "Description", getDrawableByName("puppy1"), 5, false, true))
            result.add(Timer("Food Timer", "Description", getDrawableByName("puppy1"), 5, false, true))
            result.add(Timer("Food Timer", "Description", getDrawableByName("puppy1"), 5, false, true))
            result.add(Timer("Food Timer", "Description", getDrawableByName("puppy1"), 5, false, true))
            result.add(Timer("Food Timer", "Description", getDrawableByName("puppy1"), 5, false, true))

            return result
        }
    val timersSport: ArrayList<Timer>
        get() {
            val allTimers = timers
            val selectedTimers = ArrayList<Timer>()
            for (timer in allTimers) {
                if (timer.isSport)
                    selectedTimers.add(timer)
            }
            return selectedTimers
        }


    val timersFood: ArrayList<Timer>
        get() {
            val allTimers = timers
            val selectedTimers = ArrayList<Timer>()
            for (timer in allTimers) {
                if (timer.isFood)
                    selectedTimers.add(timer)
            }
            return selectedTimers
        }


    private fun getDrawableByName(name: String): Drawable {
        val resources = context.resources
        val resourceId = resources.getIdentifier(name, "drawable",
                context.packageName)
        return ContextCompat.getDrawable(context, resourceId)!!
    }
}