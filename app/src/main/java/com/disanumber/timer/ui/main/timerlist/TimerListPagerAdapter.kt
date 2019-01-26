package com.disanumber.timer.ui.main.timerlist

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class TimerListPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {


    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return TimerListFragment.newInstance(0)
            1 -> return TimerListFragment.newInstance(1)
            2 -> return TimerListFragment.newInstance(2)
            3 -> return TimerListFragment.newInstance(3)
        }
        return TimerListFragment.newInstance(0)
    }

    override fun getCount(): Int {
        return 4
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return null

    }
}
