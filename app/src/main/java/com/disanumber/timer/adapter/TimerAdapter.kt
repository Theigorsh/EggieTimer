package com.disanumber.timer.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.disanumber.timer.R
import com.disanumber.timer.database.TimerEntity
import com.disanumber.timer.model.TimerDataUtil



class TimerAdapter(private val timers: List<TimerEntity>, private val context: Context) : RecyclerView.Adapter<TimerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.timer_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateWithTimer(timers[position])

    }

    override fun getItemCount(): Int {
        return timers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timerImage: ImageView = itemView.findViewById(R.id.timer_image)
        private val timerTitle: TextView = itemView.findViewById(R.id.timer_title)
        private val timerSeekBar: SeekBar = itemView.findViewById(R.id.seek_bar)

        fun updateWithTimer(timer : TimerEntity){
            timerTitle.text = timer.name
            timerImage.setImageDrawable(TimerDataUtil.getDrawableByName(timer.image!!, context) )
            //TODO: SEEKBAR LISTENER
            
            
        }
    }
}
