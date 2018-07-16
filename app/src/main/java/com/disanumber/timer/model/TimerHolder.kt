package com.disanumber.timer.model

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.disanumber.timer.R
import com.pavelsikun.seekbarpreference.SeekBarPreferenceView

class TimerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val timerImage: ImageView = itemView.findViewById(R.id.timer_image)
    private val timerTitle: TextView = itemView.findViewById(R.id.timer_title)
    private val timerLength: SeekBar = itemView.findViewById(R.id.seek_bar)
    fun updateWithPuppy(timer: Timer) {
        //timerImage.setImageDrawable(timer.image)
        timerTitle.text = timer.name
        timerLength.max = 100
        timerLength.progress = 50

    }



}