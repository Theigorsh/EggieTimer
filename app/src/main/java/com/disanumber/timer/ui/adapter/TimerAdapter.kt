package com.disanumber.timer.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.disanumber.timer.R
import com.disanumber.timer.model.TimerEntity
import com.disanumber.timer.ui.activities.TimerActivity
import com.disanumber.timer.ui.viewmodel.ViewModel
import com.disanumber.timer.util.PrefUtil
import com.disanumber.timer.util.TimerDataUtil
import kotlinx.android.synthetic.main.timer_item.view.*


class TimerAdapter(private val timers: List<TimerEntity>, private val context: Context, private val viewModel: ViewModel) : RecyclerView.Adapter<TimerAdapter.ViewHolder>() {


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

        private var timer: TimerEntity? = null
        private var length: Int? = null


        fun updateWithTimer(timerEntity: TimerEntity) {
            timer = timerEntity
            initTitleAndImage()
            initSeekBar()
            if (timer!!.type == 3) {
                initTimerMenuPersonal()
            } else {
                initTimerMenu()
            }
        }


        private fun setTime(hours: Int, minutes: String): String {
            return "$hours:${if (minutes.length == 2) minutes
            else "0$minutes"}:00"
        }

        private fun initTitleAndImage() {

            try {
                itemView.timer_title.text = TimerDataUtil.getStringResourceByName(timer!!.name!!, context)
            } catch (e: Resources.NotFoundException) {
                itemView.timer_title.text = timer!!.name!!
            }
            //Load image
            Glide.with(context).load(TimerDataUtil.getDrawableByName(timer!!.image!!, context)).into(itemView.timer_image)

            itemView.setOnClickListener {
                try {
                    PrefUtil.setTimerLength(context, length!!, TimerDataUtil.getStringResourceByName(timer!!.name!!, context), timer!!.image!!)
                } catch (e: Resources.NotFoundException) {
                    PrefUtil.setTimerLength(context, length!!, timer!!.name!!, timer!!.image!!)
                }
                val intent = Intent(context, TimerActivity::class.java)
                intent.putExtra("from_list", true)
                context.startActivity(intent)
            }

        }

        private fun initSeekBar() {
            length = timer!!.length!!
            var hours: Int = timer!!.length!! / 60
            var minutes: String = (timer!!.length!! % 60).toString()
            itemView.time_text.text = setTime(hours, minutes)
            itemView.seek_bar.visibility = View.INVISIBLE
            if (timer!!.min!!.toInt() != 0) {

                itemView.seek_bar.visibility = View.VISIBLE
                itemView.seek_bar.maxValue = timer!!.max!!.toFloat()
                itemView.seek_bar.minValue = timer!!.min!!.toFloat()
                itemView.seek_bar.steps = timer!!.step!!.toFloat()
                itemView.seek_bar.setMinStartValue(timer!!.length!!.toFloat()).apply()

                itemView.seek_bar.setOnSeekbarChangeListener { value ->
                    length = value!!.toInt()
                    hours = length!! / 60
                    minutes = (length!! % 60).toString()
                    itemView.time_text.text = setTime(hours, minutes)
                }
            }

        }

        private fun initTimerMenu() {
            itemView.item_menu.setOnClickListener {

                val options = arrayOf<CharSequence>(context.getString(R.string.save_time))

                val builder = AlertDialog.Builder(context)

                builder.setItems(options) { dialogInterface, i ->
                    if (i == 0) {
                        val title = timer!!.name!!
                        val image = timer!!.image!!
                        val length = length
                        val min = timer!!.min!!
                        val max = timer!!.max!!
                        val type = timer!!.type!!
                        val step = timer!!.step!!
                        val timerEntity = TimerEntity(timer!!.id, title, image, length, step, min, max, type)
                        viewModel.update(timerEntity)
                    }
                }

                builder.show()
            }
        }

        private fun initTimerMenuPersonal() {
            itemView.item_menu.setOnClickListener {

                val options = arrayOf<CharSequence>(context.getString(R.string.delete_timer))

                val builder = AlertDialog.Builder(context)

                builder.setItems(options) { _, i ->
                    if (i == 0) {
                        val id = timer!!.id
                        viewModel.deleteTimer(id)
                    }
                }

                builder.show()
            }
        }
    }

}