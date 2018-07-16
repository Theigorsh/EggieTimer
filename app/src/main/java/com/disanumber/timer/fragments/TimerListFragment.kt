package com.disanumber.timer.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.disanumber.timer.R
import com.disanumber.timer.adapter.TimerAdapter
import com.disanumber.timer.model.Timer
import com.disanumber.timer.model.TimerFactory
import com.disanumber.timer.model.TimerListType

@SuppressLint("ValidFragment")
class TimerListFragment (passedContext: Context) : Fragment(){

    val timerFactory : TimerFactory = TimerFactory(passedContext)
    val ARG_LIST_TYPE = "LIST_TYPE"
    val passThroughContext : Context = passedContext


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_timer, container, false)
        val recyclerView = rootView.findViewById(R.id.timerRecyclerView) as RecyclerView
        val listType = this.arguments?.getSerializable(ARG_LIST_TYPE) as TimerListType
        var timers = ArrayList<Timer>()
        when (listType) {
            TimerListType.All -> timers = timerFactory.timers
            TimerListType.Sport -> timers = timerFactory.timersSport
            TimerListType.Food -> timers = timerFactory.timersFood


        }

        recyclerView.adapter = TimerAdapter(timers, context!!)
        recyclerView.layoutManager = LinearLayoutManager(passThroughContext)
        return rootView
    }



    companion object {
        val ARG_LIST_TYPE = "LIST_TYPE"

        fun newInstance(listType: TimerListType, context: Context): TimerListFragment {
            val fragment = TimerListFragment(context)
            val args = Bundle()
            args.putSerializable(ARG_LIST_TYPE, listType)
            fragment.arguments = args
            return fragment
        }
    }


}