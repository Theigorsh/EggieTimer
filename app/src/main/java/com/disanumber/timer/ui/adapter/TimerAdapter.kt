package com.disanumber.timer.ui.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.disanumber.timer.R
import com.disanumber.timer.database.TimerEntity
import com.disanumber.timer.ui.activities.TimerActivity
import com.disanumber.timer.util.PrefUtil
import com.disanumber.timer.util.TimerDataUtil


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
        private val timerTimeText: TextView = itemView.findViewById(R.id.time_text)

        private val timerSeekBar: SeekBar = itemView.findViewById(R.id.seek_bar)

        fun updateWithTimer(timer: TimerEntity) {
            timerTitle.text = TimerDataUtil.getStringResourceByName(timer.name!!, context)
            Glide.with(context).load(timer.image!!).into(timerImage)
            timerSeekBar.max = 100
            //Seek bur functional
            var displayValue: Int = timer.length!!//value that will be displayed in textview
            var hours: Int = timer.length!! / 60;
            var minutes: String = (timer.length!! % 60).toString()

            timerTimeText.text = "$hours:${if (minutes.length == 2) minutes
            else "0" + minutes}:00"

            timerSeekBar.progress = calculateProgress(timer.length!!, timer.min!!, timer.max!!)
            //SeekBar listener
            timerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {}

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val value = Math.round((progress * (timer.max!! - timer.min!!) / 100).toFloat()).toDouble()
                    displayValue = (value.toInt() + timer.min!!) / timer.step!! * timer.step!!
                    hours = displayValue / 60
                    minutes = (displayValue % 60).toString()
                    timerTimeText.text = "$hours:${if (minutes.length == 2) minutes
                    else "0" + minutes}:00"
                }
            })

            timerImage.setOnClickListener({
                PrefUtil.setTimerLength(context, displayValue, TimerDataUtil.getStringResourceByName(timer.name!!, context), timer.image!!)
                val intent = Intent(context, TimerActivity::class.java)
                context.startActivity(intent)
            })


        }

        private fun calculateProgress(value: Int, MIN: Int, MAX: Int): Int {
            return 100 * (value - MIN) / (MAX - MIN)
        }
    }
}
