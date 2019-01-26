package com.disanumber.timer.ui.addtimer

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.disanumber.timer.R
import com.disanumber.timer.model.TimerEntity
import com.disanumber.timer.util.TimerDataUtil
import kotlinx.android.synthetic.main.dialog_add_timer.*

class AddTimerDialog : MvpAppCompatDialogFragment() {

    private var index = 0
    private var images: List<String>? = null
    private var onAddedTimer: OnAddedNewTimer? = null

    interface OnAddedNewTimer {
        fun addTimer(timer: TimerEntity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            onAddedTimer = targetFragment as OnAddedNewTimer

        } catch (e: ClassCastException) {
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onStart() {
        super.onStart()
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun initUI() {
        initNumberPickers()
        initImageChoice()
        btn_close.setOnClickListener {
            dialog.dismiss()
        }
        btn_add.setOnClickListener {
            if (TextUtils.isEmpty(input_title.text.toString())) {
                Toast.makeText(context!!, getString(R.string.title_not_empty), Toast.LENGTH_LONG).show()
            } else {
                val title: String = input_title.text.toString()
                val length = number_picker_hours.value * 60 + number_picker_minutes.value
                val timer = TimerEntity(title, images!![index], length, 0, 0, 0, 3)
                onAddedTimer!!.addTimer(timer)
                dialog.dismiss()
            }
        }
    }

    private fun updateUI(i: Int) {
        if (i != (images!!.size - 1)) {
            add_rigth_arrow.visibility = View.VISIBLE

        } else {
            add_rigth_arrow.visibility = View.INVISIBLE
        }
        if (i != 0) {
            add_left_arrow.visibility = View.VISIBLE
        } else {
            add_left_arrow.visibility = View.INVISIBLE
        }
    }

    private fun initNumberPickers() {
        number_picker_hours.minValue = 0
        number_picker_hours.value = 0
        number_picker_hours.maxValue = 24
        number_picker_minutes.minValue = 1
        number_picker_minutes.maxValue = 59
        number_picker_minutes.value = 5
    }


    private fun initImageChoice() {
        images = TimerDataUtil.getAddTimersIcons()
        //Image choice for timer
        add_image_timer.setImageDrawable(TimerDataUtil.getDrawableByName(images!![index], context!!))
        add_left_arrow.visibility = View.INVISIBLE
        add_rigth_arrow.setOnClickListener {
            index++
            add_image_timer.setImageDrawable(TimerDataUtil.getDrawableByName(images!![index], context!!))
            updateUI(index)

        }
        add_left_arrow.setOnClickListener {
            index--
            add_image_timer.setImageDrawable(TimerDataUtil.getDrawableByName(images!![index], context!!))
            updateUI(index)

        }
    }

}