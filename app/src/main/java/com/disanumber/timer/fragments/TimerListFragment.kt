package com.disanumber.timer.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.disanumber.timer.R
import com.disanumber.timer.adapter.TimerAdapter
import com.disanumber.timer.database.TimerEntity
import com.disanumber.timer.util.Constants
import com.disanumber.timer.util.PrefUtil
import com.disanumber.timer.viewmodel.ViewModel


class TimerListFragment() : Fragment() {


    private var recyclerView: RecyclerView? = null
    private var type: Int? = null
    private val timersData = ArrayList<TimerEntity>()
    private var adapter: TimerAdapter? = null
    private var viewModel: ViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_timer_list, container, false)
        type = this.arguments?.getInt(Constants.ARG_FRAGMENT)
        recyclerView = rootView.findViewById(R.id.sport_recycler_view) as RecyclerView
        initRecyclerView()
        initViewModel()
        if(!PrefUtil.getData(context!!)){
            addSampleData()
            PrefUtil.setDataAdded(context!!)
        }


        return rootView
    }

    private fun initRecyclerView() {
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager


    }

    private fun initViewModel() {

        val timersObserver: Observer<List<TimerEntity>> = object : Observer<List<TimerEntity>> {
            override fun onChanged(timerEntities: List<TimerEntity>?) {
                if (timerEntities != null) {
                    timersData.clear()

                    var i: Int = -1
                    for(timer: TimerEntity in timerEntities){
                        i++
                        if(timer.type == type) timersData.add(timerEntities[i])
                    }

                }
                //timersData.addAll(timerEntities!!)
                if (adapter == null) {
                    adapter = TimerAdapter(timersData,
                            context!!)
                    recyclerView!!.adapter = adapter

                } else {
                    adapter!!.notifyDataSetChanged()
                }
            }


        }

        viewModel = ViewModelProviders.of(this)
                .get(ViewModel::class.java)
        viewModel!!.timers!!.observe(this, timersObserver)


    }

    private fun addSampleData() {
        viewModel!!.addSampleData()
    }

    companion object {
        fun newInstance(type: Int): TimerListFragment{
            val fragment = TimerListFragment()
            val args = Bundle()
            args.putInt(Constants.ARG_FRAGMENT, type)
            fragment.arguments = args
            return fragment

        }
    }
}