package com.disanumber.timer.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.disanumber.timer.R

import android.support.v4.view.ViewPager
import com.disanumber.timer.adapter.ViewPagerAdapter


class CategoriesFragment : Fragment() {
    var viewPager: ViewPager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        viewPager = view.findViewById(R.id.viewpager) as ViewPager
        val tabLayout = view.findViewById(R.id.tabs) as TabLayout
        setupViewPager(viewPager!!)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.text = "HEALTH"

        tabLayout.getTabAt(1)!!.text = "FOOD"
        tabLayout.getTabAt(2)!!.text = "SPORT"
        return view

    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager, context!!)
        //adapter.addFragment(FoodFragment())
        //adapter.addFragment(SportFragment())
        viewPager.adapter = adapter


    }


}