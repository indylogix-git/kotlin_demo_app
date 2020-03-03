package com.kotlinapp.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.kotlinapp.R
import com.kotlinapp.Utills.utills
import com.kotlinapp.main.MainActivity
import com.kotlinapp.retofit.ApiUtils
import com.kotlinapp.retofit.UserService


class ProfileActivity : Activity() {

    private var edtUserfirstname: EditText? = null
    private var edtUserlastname: EditText? = null
    private var edtUseremail: EditText? = null
    private var edtUsermobile: EditText? = null
    private var btnUpdate: Button? = null
    var userService: UserService? = null
    var sharedpreferences: SharedPreferences? = null
    private var back_arrow: ImageView? = null
    private var header_name: TextView? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sharedpreferences =
            getSharedPreferences(utills.MyPREFERENCES, Context.MODE_PRIVATE)
        edtUserfirstname = findViewById<View>(R.id.edtUserfirstname) as EditText
        edtUserlastname = findViewById<View>(R.id.edtUserlastname) as EditText
        edtUseremail = findViewById<View>(R.id.edtUseremail) as EditText
        edtUsermobile = findViewById<View>(R.id.edtUsermobile) as EditText

        back_arrow = findViewById<View>(R.id.back_arrow) as ImageView
        header_name = findViewById<View>(R.id.header_name) as TextView

        back_arrow!!.isVisible = true
        header_name!!.setText(R.string.title_profile)

        edtUserfirstname!!.setText(sharedpreferences!!.getString(utills.F_Name, ""))
        edtUserlastname!!.setText(sharedpreferences!!.getString(utills.L_Name, ""))
        edtUseremail!!.setText(sharedpreferences!!.getString(utills.Email, ""))
        edtUsermobile!!.setText(sharedpreferences!!.getString(utills.Phone, ""))

        btnUpdate =
            findViewById<View>(R.id.btnUpdate) as Button
        userService = ApiUtils.userService
        btnUpdate!!.setOnClickListener {
            val Userfirstname = edtUserfirstname!!.text.toString()
            val Userlastname = edtUserlastname!!.text.toString()
            val Useremail = edtUseremail!!.text.toString()
            val Useremobile = edtUsermobile!!.text.toString()
            //validate form
            if (validateLogin(
                    Userfirstname,
                    Userlastname,
                    Useremail,
                    Useremobile
                )
            ) {
                val editor = sharedpreferences!!.edit()
                val random = sharedpreferences!!.getString(utills.ID, "")
                editor.putString(utills.ID, random.toString())
                editor.putString(utills.F_Name, Userfirstname);
                editor.putString(utills.L_Name, Userlastname);
                editor.putString(utills.Phone, Useremobile)
                editor.putString(utills.Email, Useremail)
                editor.commit()

                val intent = Intent(
                    this@ProfileActivity,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
            }
        }

        back_arrow!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateLogin(
        userfirstname: String?,
        userlastname: String?,
        useremail: String?,
        useremobile: String?
    ): Boolean {
        val MobilePattern = "[0-9]{10}"
        if (userfirstname == null || userfirstname.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "User first name is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userfirstname.length < 5) {
            Toast.makeText(this, "User enter more than 5 character first name.", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (userlastname == null || userlastname.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "User last name is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userlastname.length < 5) {
            Toast.makeText(this, "User enter more than 5 character last name.", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (useremail == null || useremail.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "User email is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!utills.EMAIL_ADDRESS_PATTERN.matcher(useremail).matches()) {
            Toast.makeText(this, "User email is not valid", Toast.LENGTH_SHORT).show()
        }
        if (useremobile == null || useremobile.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "User mobiles no. is required", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}