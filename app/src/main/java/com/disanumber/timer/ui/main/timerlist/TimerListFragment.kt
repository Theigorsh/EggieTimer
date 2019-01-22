package com.disanumber.timer.ui.main.timerlist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.disanumber.timer.R
import com.disanumber.timer.database.AppRepository
import com.disanumber.timer.model.TimerEntity
import com.disanumber.timer.ui.fragments.AddTimerDialog
import com.disanumber.timer.util.Constants
import com.disanumber.timer.util.PrefUtil
import kotlinx.android.synthetic.main.fragment_timer_list.*


class TimerListFragment : MvpAppCompatFragment(), TimerListView, AddTimerDialog.OnAddedNewTimer {


    private lateinit var recyclerView: RecyclerView
    private var type: Int = 0
    private val timersData = ArrayList<TimerEntity>()
    private var adapter: TimerAdapter? = null
    private lateinit var prefs: PrefUtil
    @InjectPresenter
    lateinit var presenter: TimerListPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_timer_list, container, false)
        type = this.arguments?.getInt(Constants.ARG_FRAGMENT)!!
        recyclerView = rootView.findViewById(R.id.sport_recycler_view) as RecyclerView
        prefs = PrefUtil(context!!)
        presenter.setData(AppRepository.getInstance(context!!.applicationContext))

        return rootView
    }

    override fun onDataLoaded() {
        initRecyclerView()
        initData()
        checkData()
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (type == 3) {
            fab_add.visibility = View.VISIBLE
            fab_add.setOnClickListener {
                val dialog = AddTimerDialog()
                dialog.setTargetFragment(this, 0)
                dialog.show(fragmentManager, "ggg")
            }
        }
    }

    private fun initData() {
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
                        context!!, presenter)
                recyclerView!!.adapter = adapter
            } else {
                adapter!!.notifyDataSetChanged()
            }

            if (timersData.size == 0) {
                txt_add_timers.visibility = View.VISIBLE

            } else {
                txt_add_timers.visibility = View.INVISIBLE
            }
        }
        presenter.timers!!.observe(this, timersObserver)
    }

    private fun addData() {
        presenter.addData()
    }


    override fun addTimer(timer: TimerEntity) {
        presenter.addTimer(timer)
    }


    private fun checkData() {
        if (!prefs.getData()) {
            addData()
            prefs.setDataAdded()
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