package com.disanumber.timer.ui.activities

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
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.anjlab.android.iab.v3.BillingProcessor
import com.bumptech.glide.Glide
import com.disanumber.timer.R
import com.disanumber.timer.database.AppRepository
import com.disanumber.timer.ui.adapter.ViewPagerAdapter
import com.disanumber.timer.ui.interfaces.MainView
import com.disanumber.timer.ui.presenters.MainPresenter
import com.disanumber.timer.util.PrefUtil
import com.disanumber.timer.util.TimerDataUtil
import com.kobakei.ratethisapp.RateThisApp
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    private var presenter: MainPresenter? = null
    private lateinit var navigationView: NavigationView
    private var mRepository: AppRepository? = null
    private var bp: BillingProcessor? = null
    private var navMenu: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar_Nav)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        checkIsFirstLaunch()
        initUI()
        setRateThisApp()
        mRepository = AppRepository.getInstance(applicationContext)


    }

    private fun checkIsFirstLaunch() {
        val isFirstLaunch = PrefUtil.getIsFirstLaunch(this)
        if (!isFirstLaunch) {
            finish()
            PrefUtil.setFirstLaunch(this)
            startActivity(Intent(applicationContext, IntroActivity::class.java))
        } else {
            presenter = MainPresenter(this)
            presenter!!.initUI()
        }
    }

    private fun setRateThisApp() {
        val config: RateThisApp.Config = RateThisApp.Config(5, 10)
        RateThisApp.init(config);
        RateThisApp.onCreate(this);
        RateThisApp.showRateDialogIfNeeded(this);
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_timer -> presenter!!.checkTimerState(applicationContext)
            R.id.nav_settings -> presenter!!.sendToSettingsScreen()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setNavView() {
        navigationView = nav_view
        navMenu = navigationView.menu
        val navHeader = navigationView.getHeaderView(0)
        val navHeaderImage = navHeader.findViewById<ImageView>(R.id.header_image)
        val list = TimerDataUtil.getNavHeadersIcons()
        val index = (0 until list.size).shuffled().last()
        Glide.with(this).load(TimerDataUtil.getDrawableByName(list[index], this)).into(navHeaderImage)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun initUI() {
        setSupportActionBar(toolbar)//set toolbar from layout
        setViewPager()
        setNavView()

    }

    private fun setViewPager() {
        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.icon = getIcon(R.drawable.tab_sport, this)
        tabLayout.getTabAt(1)!!.icon = getIcon(R.drawable.tab_food, this)
        tabLayout.getTabAt(2)!!.icon = getIcon(R.drawable.tab_health, this)
        tabLayout.getTabAt(3)!!.icon = getIcon(R.drawable.tab_person, this)
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun checkVersion(isPurchased: Boolean) {
        val isPremiumDataAdded = PrefUtil.getPremiumData(applicationContext)
        if (isPurchased) {
            PrefUtil.setVersion(this)
            checkPremiumDataAdded(isPremiumDataAdded)
        } else {

        }

    }


    override fun sendToTimer() {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
    }


    override fun sendToSettings() {
        val intent = Intent(applicationContext, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    }




    override fun onDestroy() {
        if (bp != null) {
            bp!!.release()
        }
        super.onDestroy()
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) drawer_layout.closeDrawer(GravityCompat.START)
        else {
            super.onBackPressed()
            finish()
        }
    }




    private fun getIcon(id: Int, context: Context): Drawable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return resources.getDrawable(id, context.theme)
        } else {
            return resources.getDrawable(id);
        }
    }



    private fun checkPremiumDataAdded(isAdded: Boolean){
        if(!isAdded){
            mRepository!!.addPremiumData()
            PrefUtil.setPremiumDataAdded(applicationContext)
        }
    }
}