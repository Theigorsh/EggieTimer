package com.disanumber.timer.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.disanumber.timer.R

class RegisterFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)
        val button = view.findViewById<Button>(R.id.to_log_btn)

        button.setOnClickListener({
            val fragment = LoginFragment()
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.log_reg_container, fragment)
                    .addToBackStack(null)
                    .commit()

        })
        return  view
    }
}