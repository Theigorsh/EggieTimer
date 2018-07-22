package com.disanumber.timer.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.disanumber.timer.R
import com.disanumber.timer.database.TimerEntity



class TimerDataUtil {

    companion object {
        fun getTimers(): List<TimerEntity> {
            val timers = ArrayList<TimerEntity>()
            //Sport Timers
            timers.add(TimerEntity("stretching", R.drawable.stretching, 5, 1,2,30,0))
            timers.add(TimerEntity("running", R.drawable.running, 20, 1,5,60,0))
            timers.add(TimerEntity("jumping", R.drawable.jumping, 10, 1,3,25,0))
            timers.add(TimerEntity("step_aerobics", R.drawable.step_aerobic, 20, 1,10,40,0))
            timers.add(TimerEntity("warm_up", R.drawable.warm_up, 10, 1,2,30,0))
            timers.add(TimerEntity("abs_workout", R.drawable.abs_workout, 10, 1,3,30,0))
            timers.add(TimerEntity("plank", R.drawable.plank, 3, 1,1,10,0))
            timers.add(TimerEntity("swimming", R.drawable.swimming, 60, 5,20,120,0))

            //Food Timers
            timers.add(TimerEntity("eggs", R.drawable.egg, 10, 1,5,15,1))
            timers.add(TimerEntity("meat", R.drawable.meat, 45, 5,30,120,1))
            timers.add(TimerEntity("fish", R.drawable.fish, 40, 5,20,120,1))
            timers.add(TimerEntity("soup", R.drawable.soup, 60, 5,30,80,1))
            timers.add(TimerEntity("cereals", R.drawable.cereals, 20, 5,5,40,1))
            timers.add(TimerEntity("bake", R.drawable.bake, 60, 5,10,90,1))
            timers.add(TimerEntity("toasts", R.drawable.toasts, 3, 1,1,7,1))
            timers.add(TimerEntity("vegetables", R.drawable.vegetables, 40, 5,20,60,1))

            //Health Timers
            timers.add(TimerEntity("sleeping", R.drawable.sleep, 480, 30,300,600,2))
            timers.add(TimerEntity("inhalation", R.drawable.inhalation, 15, 1,5,30,2))
            timers.add(TimerEntity("computer", R.drawable.computer_work, 120, 10,60,180,2))
            timers.add(TimerEntity("walking", R.drawable.walk, 60, 5,20,90,2))
            timers.add(TimerEntity("cleaning", R.drawable.cleaning, 120, 10, 20, 180, 2))
            timers.add(TimerEntity("reading", R.drawable.reading, 60, 5,20,120,2))
            timers.add(TimerEntity("watching_tv", R.drawable.tv, 90, 5,30,120,2))
            timers.add(TimerEntity("face_mask", R.drawable.mask, 15, 1,5,30,2))

            return timers

        }

        fun getDrawableByName(name: String, context: Context): Drawable {
            val resources = context.resources
            val resourceId = resources.getIdentifier(name, "drawable",
                    context.packageName)
            return ContextCompat.getDrawable(context, resourceId)!!
        }

        fun getStringResourceByName(aString: String, context: Context): String {
            val packageName = context.packageName
            val resId = context.resources.getIdentifier(aString, "string", packageName)
            return context.getString(resId)
        }
    }

}