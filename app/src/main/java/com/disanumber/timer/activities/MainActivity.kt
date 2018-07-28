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
import android.widget.Toast
import com.disanumber.timer.R
import com.disanumber.timer.adapter.ViewPagerAdapter
import com.disanumber.timer.util.PrefUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var viewPager: ViewPager? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)//set toolbar from layout
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user == null) {
            val intent = Intent(applicationContext, LogRegActivity::class.java)
            startActivity(intent)
        }
        viewPager = findViewById<ViewPager>(R.id.viewpager)
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
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
            R.id.nav_log_out -> logOut()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) drawer_layout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }

    private fun goToTimerActivity() {
        if (PrefUtil.getTimerLength(applicationContext) == 0) {
            Toast.makeText(applicationContext, "Please, choose timer to use", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }

    }


    private fun goToLogin() {
        val intent = Intent(applicationContext, LogRegActivity::class.java)
        startActivity(intent)

    }


    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        goToLogin()
    }
}
