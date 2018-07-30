package com.disanumber.timer.ui.presenters

import android.text.TextUtils
import com.disanumber.timer.R
import com.disanumber.timer.ui.interfaces.LoginView
import com.disanumber.timer.util.PrefUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginPresenter(internal var loginView: LoginView) {

    private val context = loginView.getContext()

    fun authWithEmailAndPassword(email: String, password: String) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginView.authSuccesful()
                    loginView.sendToMain()
                } else {
                    loginView.showToast("Error login")
                }
            }
        }

    }

    fun getVersion() {
        //Current user uuid
        val uuid = FirebaseAuth.getInstance().currentUser!!.uid
        //Databse reference
        val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().reference
                .child("users").child(uuid)
        //value event listener to retrieve data
        val dataListener = object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                val version: String = data.child("version").value.toString()
                if (version == context.getString(R.string.version_free)) {

                    loginView.showToast("Free version of app")


                } else {
                    loginView.showToast("Paid version of app")
                    PrefUtil.setVersion(context)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        databaseRef.addListenerForSingleValueEvent(dataListener)

    }

    fun checkCurrentUser(){
        if(FirebaseAuth.getInstance().currentUser != null){
            loginView.sendToMain()
        }
    }

    fun sendToRegisterScreen(){
        loginView.sendToRegisterScreen()
    }


}