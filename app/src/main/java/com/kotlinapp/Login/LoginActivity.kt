package com.kotlinapp.Login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.kotlinapp.Forgot.ForgotActivity
import com.kotlinapp.MainActivity
import com.kotlinapp.R
import com.kotlinapp.Regisrtation.RegistrationActivity
import com.kotlinapp.Utills.utills
import com.kotlinapp.retofit.ApiUtils
import com.kotlinapp.retofit.UserService


class LoginActivity : Activity() {

    private var edtUsername: EditText? = null
    private var edtPassword: EditText? = null
    private var btnLogin: Button? = null
    private var registration_txt: TextView? = null
    private var forgot_txt: TextView? = null
    var userService: UserService? = null
    var sharedpreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedpreferences =
            getSharedPreferences(utills.MyPREFERENCES, Context.MODE_PRIVATE)
        edtUsername = findViewById<View>(R.id.edtUsername) as EditText
        edtPassword = findViewById<View>(R.id.edtPassword) as EditText
        btnLogin = findViewById<View>(R.id.btnLogin) as Button
        registration_txt = findViewById(R.id.registration_txt)
        forgot_txt = findViewById(R.id.forgot_txt)
        userService = ApiUtils.userService
        btnLogin!!.setOnClickListener {
            val username = edtUsername!!.text.toString()
            val password = edtPassword!!.text.toString()
            //validate form
            if (validateLogin(username, password)) {
                if (sharedpreferences!!.getString(utills.ID, "")!!.isEmpty() &&
                    sharedpreferences!!.getString(utills.ID, "").equals("", ignoreCase = true)
                ) {
                    Toast.makeText(this@LoginActivity, "User Not Register.", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(
                        this@LoginActivity,
                        RegistrationActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(
                        this@LoginActivity,
                        MainActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }
                /*SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString(utills.ID, "1");
                            editor.putString(utills.F_Name, username);
                            editor.commit();*/
                //do login
                //                    doLogin(username, password);
            }
        }
        registration_txt!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@LoginActivity,
                RegistrationActivity::class.java
            )
            startActivity(intent)
            finish()
        })
        forgot_txt!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@LoginActivity,
                ForgotActivity::class.java
            )
            startActivity(intent)
            finish()
        })
    }

    private fun validateLogin(username: String?, password: String?): Boolean {
        if (username == null || username.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password == null || password.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}