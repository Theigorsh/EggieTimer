package com.disanumber.timer.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.disanumber.timer.model.TimerEntity


class TimerDataUtil {

    companion object {
        fun getTimers(): List<TimerEntity> {
            val timers = ArrayList<TimerEntity>()
            //Sport Timers
            timers.add(TimerEntity("running", "running", 20, 1, 5, 60, 0))
            timers.add(TimerEntity("jumping", "jumping", 10, 1, 3, 25, 0))
            timers.add(TimerEntity("warm_up", "warm_up", 10, 1, 1, 30, 0))
            timers.add(TimerEntity("abs_workout", "abs_workout", 10, 1, 3, 30, 0))
            timers.add(TimerEntity("plank", "plank", 3, 1, 1, 10, 0))
            timers.add(TimerEntity("swimming", "swimming", 60, 5, 20, 120, 0))

            //Food Timers
            timers.add(TimerEntity("eggs", "egg", 10, 1, 5, 15, 1))
            timers.add(TimerEntity("meat", "meat", 45, 5, 30, 120, 1))
            timers.add(TimerEntity("fish", "fish", 40, 5, 20, 120, 1))
            timers.add(TimerEntity("soup", "soup", 60, 5, 30, 80, 1))
            timers.add(TimerEntity("bake", "bake", 60, 5, 10, 90, 1))
            timers.add(TimerEntity("vegetables", "vegetables", 40, 5, 20, 60, 1))

            //Health Timers
            timers.add(TimerEntity("sleeping", "sleep", 480, 30, 300, 600, 2))
            timers.add(TimerEntity("computer", "computer_work", 120, 10, 60, 180, 2))
            timers.add(TimerEntity("walking", "walk", 60, 5, 20, 120, 2))
            timers.add(TimerEntity("cleaning", "cleaning", 120, 10, 20, 180, 2))
            timers.add(TimerEntity("reading", "reading", 60, 5, 20, 120, 2))
            timers.add(TimerEntity("watching_tv", "tv", 90, 5, 30, 120, 2))
            return timers

        }
        
        fun getRandomNavHeaderIcon(): String {
            val images = ArrayList<String>()
            images.apply {
                add("one")
                add("two")
                add("three")
                add("four")
                add("five")
            }
            val randomIndex = (0 until images.size).shuffled().last()
            return images[randomIndex]
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

        fun getAddTimersIcons(): List<String> {
            val images = ArrayList<String>()
            images.add("custom_one")
            images.add("custom_two")
            images.add("custom_three")
            return images
        }

    }

}