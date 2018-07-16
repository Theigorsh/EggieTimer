package com.disanumber.timer.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.disanumber.timer.R
import com.disanumber.timer.model.Timer
import com.disanumber.timer.model.TimerHolder
import com.disanumber.timer.timer.TimerActivity
import kotlinx.android.synthetic.main.timer_item.view.*

class TimerAdapter (private val timers: ArrayList<Timer>, private val context: Context) : RecyclerView.Adapter<TimerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerHolder {
        val timerItem = LayoutInflater.from(parent.context).inflate(R.layout.timer_item, parent, false) as LinearLayout
        return TimerHolder(timerItem)
    }

    override fun onBindViewHolder(holder: TimerHolder, position: Int) {
        holder.updateWithPuppy(timers[position])
        val timer: Timer = timers[position]
        holder.itemView.timer_image.setOnClickListener({
            val intent = Intent(context, TimerActivity::class.java)
            startActivity(context, intent, null)
        })

    }

    override fun getItemCount(): Int {
        return timers.toArray().count();
    }


}