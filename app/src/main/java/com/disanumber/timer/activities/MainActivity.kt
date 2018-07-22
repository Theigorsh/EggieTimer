package com.disanumber.timer.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.disanumber.timer.R
import com.disanumber.timer.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)//set toolbar from layout
        viewPager = findViewById(R.id.viewpager) as ViewPager
        val tabLayout = findViewById(R.id.tabs) as TabLayout
        setupViewPager(viewPager!!)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.text = "SPORT"
        tabLayout.getTabAt(1)!!.text = "FOOD"
        tabLayout.getTabAt(2)!!.text = "HEALTH"
        nav_view.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_timer -> goToTimerActivity()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) drawer_layout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }

    private fun goToTimerActivity() {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
    }
}
