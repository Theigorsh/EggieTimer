package com.disanumber.timer.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.disanumber.timer.R
import com.disanumber.timer.ui.activities.MainActivity
import com.disanumber.timer.ui.interfaces.LoginView
import com.disanumber.timer.ui.presenters.LoginPresenter
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(), View. OnClickListener, LoginView {

    private var presenter: LoginPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        presenter = LoginPresenter(this)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log_btn.setOnClickListener(this)
        to_reg_btn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        presenter!!.checkCurrentUser()
    }




    override fun showToast(message: String) {
        Toast.makeText(activity!!.applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun authSuccesful() {
        presenter!!.getVersion()
    }

    override fun sendToMain() {
        val intent = Intent(activity!!.applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    override fun getContext(): Context {
        return activity!!.applicationContext
    }
    override fun sendToRegisterScreen() {
        val fragment = RegisterFragment()
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.log_reg_container, fragment)
                .addToBackStack(null)
                .commit()
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.log_btn -> presenter!!.authWithEmailAndPassword(input_email.text.toString(), input_password.text.toString())
            R.id.to_reg_btn ->presenter!!.sendToRegisterScreen()
        }
    }




}
