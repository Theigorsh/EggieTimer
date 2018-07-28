package com.disanumber.timer.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.disanumber.timer.R
import com.disanumber.timer.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var logBtn: Button
    private lateinit var regBtn: Button

    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        logBtn = view.findViewById(R.id.log_btn)
        inputEmail = view.findViewById(R.id.input_email)
        inputPassword = view.findViewById(R.id.input_password)
        regBtn = view.findViewById(R.id.to_reg_btn)
        setupAuth()



        return view

    }

    private fun setupAuth() {
        auth = FirebaseAuth.getInstance()
        //log User to App
        logBtn.setOnClickListener({
            if (!TextUtils.isEmpty(inputEmail.text) && !TextUtils.isEmpty(inputPassword.text)) {
                val email: String = inputEmail.text.toString()
                val password: String = inputPassword.text.toString()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendToMain()
                    } else {
                        Toast.makeText(activity!!.applicationContext, "Error login", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        regBtn.setOnClickListener({
            val fragment = RegisterFragment()
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.log_reg_container, fragment)
                    .addToBackStack(null)
                    .commit()

        })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            sendToMain()

        }
    }

    private fun sendToMain() {
        val intent = Intent(activity!!.applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}
