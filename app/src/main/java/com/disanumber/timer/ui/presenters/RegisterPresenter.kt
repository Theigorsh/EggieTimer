package com.disanumber.timer.ui.presenters

import android.util.Patterns
import com.disanumber.timer.R
import com.disanumber.timer.model.User
import com.disanumber.timer.ui.interfaces.RegisterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class RegisterPresenter(internal var registerView: RegisterView) {

    private lateinit var databaseRef: DatabaseReference
    private val context = registerView.getContext()
    private val PASSWORD_PATTERN = Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter

            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces

            ".{4,}" +               //at least 4 characters

            "$")

    private fun registerUser(name: String, email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                val currentUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                val uuid: String = currentUser.uid
                databaseRef = FirebaseDatabase.getInstance().reference.child("users").child(uuid)
                val user = User(email, name, "free")
                databaseRef.setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        registerView.sendToMain()
                    }
                }
            } else {
                registerView.showToast(context.getString(R.string.cannot_sign_in))
            }
        }
    }

    fun sendToLoginScreen() {
        registerView.sendToLoginScreen()
    }

    fun checkInputData(email: String, name: String, password: String, confirmPassword: String) {

        if (isEmpty(email, name, password, confirmPassword) && validateEmail(email) && validatePassword(password)) {
            if (doStringsMatch(password, confirmPassword)) {
                registerUser(name, email, password)
            } else {
                registerView.showToast(context.getString(R.string.passwords_dont_match))
            }
        }


    }

    private fun validateEmail(email: String): Boolean {
        val emailInput = email.trim()
        return if (emailInput.isEmpty()) {//if empty
            false
        } else Patterns.EMAIL_ADDRESS.matcher(emailInput).matches() //check if email is valid
    }

    //validate password
    private fun validatePassword(password: String): Boolean {
        val passwordInput = password.trim()
        return if (passwordInput.isEmpty()) {
            false

        } else PASSWORD_PATTERN.matcher(passwordInput).matches()
    }

    private fun isEmpty(email: String, username: String, password: String, confirmPassword: String): Boolean {
        if (email == "" || username == "" || password == "" || confirmPassword == "") {
            registerView.showToast(context.getString(R.string.fields_must_filled))
            return false
        }
        return true
    }

    private fun doStringsMatch(s1: String, s2: String): Boolean {
        return s1 == s2
    }

}