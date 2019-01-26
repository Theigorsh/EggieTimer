package com.disanumber.timer.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.disanumber.timer.R
import com.disanumber.timer.ui.main.timerlist.TimerListPagerAdapter
import com.disanumber.timer.ui.settings.SettingsActivity
import com.disanumber.timer.ui.timer.TimerActivity
import com.disanumber.timer.util.PrefUtil
import com.disanumber.timer.util.TimerDataUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MvpAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar_Nav)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.prefs = PrefUtil(this)
        init()
    }

    private fun init() {
        presenter.initUI()
    }


    private fun setNavView() {
        val navHeader = nav_view.getHeaderView(0)
        val navHeaderImage = navHeader.findViewById<ImageView>(R.id.header_image)

        Glide.with(this).load(TimerDataUtil.getDrawableByName(TimerDataUtil.getRandomNavHeaderIcon(), this)).into(navHeaderImage)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun initUI() {
        setSupportActionBar(toolbar)
        setViewPager()
        setNavView()

    }

    private fun setViewPager() {
        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val adapter = TimerListPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.icon = getIcon(R.drawable.tab_sport, this)
        tabLayout.getTabAt(1)!!.icon = getIcon(R.drawable.tab_food, this)
        tabLayout.getTabAt(2)!!.icon = getIcon(R.drawable.tab_health, this)
        tabLayout.getTabAt(3)!!.icon = getIcon(R.drawable.tab_person, this)
        nav_view.setNavigationItemSelectedListener(this)
    }


    override fun sendToTimer() {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
    }


    override fun sendToSettings() {
        val intent = Intent(applicationContext, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun sendToIntro() {

    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) drawer_layout.closeDrawer(GravityCompat.START)
        else {
            super.onBackPressed()
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_timer -> presenter.checkTimerState(applicationContext)
            R.id.nav_settings -> presenter.sendToSettingsScreen()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun getIcon(id: Int, context: Context): Drawable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return resources.getDrawable(id, context.theme)
        } else {
            return resources.getDrawable(id);
        }
    }


}