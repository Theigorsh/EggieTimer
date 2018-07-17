package com.disanumber.timer.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.disanumber.timer.fragments.TimerListFragment


class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private val mFragmentList = ArrayList<Fragment>()


    override fun getItem(position: Int): Fragment {
        //return mFragmentList[position]
        when(position){
            0 -> return TimerListFragment.newInstance(0)
            1 -> return TimerListFragment.newInstance(1)
            2 -> return TimerListFragment.newInstance(2)

        }
        return TimerListFragment.newInstance(0)
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
