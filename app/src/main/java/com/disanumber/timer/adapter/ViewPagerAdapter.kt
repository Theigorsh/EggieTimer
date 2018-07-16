package com.disanumber.timer.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.disanumber.timer.fragments.TimerListFragment
import com.disanumber.timer.model.Timer
import com.disanumber.timer.model.TimerListType

class ViewPagerAdapter(manager: FragmentManager,  private val context: Context) : FragmentPagerAdapter(manager) {
    private val mFragmentList = ArrayList<Fragment>()


    override fun getItem(position: Int): Fragment {
        //return mFragmentList[position]
        when (position) {
            0 -> return TimerListFragment.newInstance(TimerListType.All, context)
            1 -> return TimerListFragment.newInstance(TimerListType.Sport, context)
            2 -> return TimerListFragment.newInstance(TimerListType.Food, context)

        }
        return TimerListFragment.newInstance(TimerListType.All, context)
    }

    override fun getCount(): Int {
        //return mFragmentList.size
        return 3
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)


    }
    override fun getPageTitle(position: Int): CharSequence? {
        // return null to show no title.
        return null

    }
}
