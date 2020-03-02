package com.kotlinapp.Forgot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.kotlinapp.Login.LoginActivity
import com.kotlinapp.R


class ForgotActivity : Activity() {

    private var edtForgot_password: EditText? = null
    private var btnforgot: Button? = null
    private var signIn_txt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        edtForgot_password = findViewById<View>(R.id.edtForgot_password) as EditText
        btnforgot = findViewById<View>(R.id.btnforgot) as Button
        signIn_txt = findViewById<View>(R.id.signIn_txt) as TextView

        signIn_txt!!.setOnClickListener {
            val intent = Intent(
                this@ForgotActivity,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish()
        }

        btnforgot!!.setOnClickListener {
            val Forgot_password = edtForgot_password!!.text.toString()
            //validate form
            if (validateLogin(Forgot_password)) { //do login
                //                    doLogin(username, password);
                val intent = Intent(
                    this@ForgotActivity,
                    LoginActivity::class.java
                )
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validateLogin(forgot_password: String?): Boolean {
        if (forgot_password == null || forgot_password.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "Username or Email is required", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onBackPressed() { //        super.onBackPressed();
        val intent = Intent(
            this@ForgotActivity,
            LoginActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}