package com.disanumber.timer.ui.fragments

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
import com.disanumber.timer.model.TimerEntity
import com.disanumber.timer.ui.adapter.TimerAdapter
import com.disanumber.timer.ui.viewmodel.ViewModel
import com.disanumber.timer.util.Constants
import com.disanumber.timer.util.PrefUtil
import kotlinx.android.synthetic.main.fragment_timer_list.*


class TimerListFragment : Fragment(), AddTimerDialog.OnAddedNewTimer {


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
        checkData()
        return rootView
    }


    private fun initRecyclerView() {
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (type == 3) {
            fab_add.visibility = View.VISIBLE

            fab_add.setOnClickListener {
                    val dialog = AddTimerDialog()
                    dialog.setTargetFragment(this, 0);
                    dialog.show(fragmentManager, "ggg")
            }
        }
    }

    private fun initViewModel() {

        val timersObserver: Observer<List<TimerEntity>> = Observer { timerEntities ->
            if (timerEntities != null) {
                timersData.clear()

                var i: Int = -1
                for (timer: TimerEntity in timerEntities) {
                    i++
                    if (timer.type == type) timersData.add(timerEntities[i])
                }


            }
            if (adapter == null) {
                adapter = TimerAdapter(timersData,
                        context!!, viewModel!!)
                recyclerView!!.adapter = adapter

            } else {
                adapter!!.notifyDataSetChanged()
            }
            if (timersData.size == 0) {
                txt_view.visibility = View.VISIBLE

            } else {
                txt_view.visibility = View.INVISIBLE

            }
        }

        viewModel = ViewModelProviders.of(this)
                .get(ViewModel::class.java)
        viewModel!!.timers!!.observe(this, timersObserver)


    }

    private fun addData() {
        viewModel!!.addData()
    }


    override fun addTimer(timer: TimerEntity) {

        viewModel!!.addTimer(timer)




    }


    private fun checkData() {
        if (!PrefUtil.getData(context!!)) {
            addData()
            PrefUtil.setDataAdded(context!!)
        }
    }

    companion object {
        fun newInstance(type: Int): TimerListFragment {
            val fragment = TimerListFragment()
            val args = Bundle()
            args.putInt(Constants.ARG_FRAGMENT, type)
            fragment.arguments = args
            return fragment

        }
    }
}