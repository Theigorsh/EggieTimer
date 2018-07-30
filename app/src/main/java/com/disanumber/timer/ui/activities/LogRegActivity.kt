package com.disanumber.timer.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.disanumber.timer.R
import com.disanumber.timer.ui.fragments.LoginFragment

class LogRegActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_reg)
        supportActionBar!!.hide()
        setupLoginFragment()
    }

    private fun setupLoginFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.log_reg_container, LoginFragment())
        transaction.commit()

    }
}
