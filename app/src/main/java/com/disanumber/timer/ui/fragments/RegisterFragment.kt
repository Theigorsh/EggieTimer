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
import com.disanumber.timer.ui.interfaces.RegisterView
import com.disanumber.timer.ui.presenters.RegisterPresenter
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(), View.OnClickListener, RegisterView {

    private var presenter: RegisterPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = RegisterPresenter(this)
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reg_btn.setOnClickListener(this)
        to_log_btn.setOnClickListener(this)
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.reg_btn -> presenter!!.checkInputData(input_email.text.toString(), input_name.text.toString(),
                    input_password.text.toString(), input_confirm_password.text.toString())
            R.id.to_log_btn -> presenter!!.sendToLoginScreen()

        }
    }

    override fun showToast(message: String) {
        Toast.makeText(activity!!.applicationContext, message, Toast.LENGTH_SHORT).show();

    }

    override fun sendToMain() {
        val mainIntent = Intent(activity!!.applicationContext, MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
    }

    override fun getContext(): Context {
        return activity!!.applicationContext
    }

    override fun sendToLoginScreen() {
        val fragment = LoginFragment()
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.log_reg_container, fragment)
                .addToBackStack(null)
                .commit()

    }
}