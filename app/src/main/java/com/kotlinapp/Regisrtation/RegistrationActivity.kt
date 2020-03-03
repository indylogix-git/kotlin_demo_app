package com.kotlinapp.Regisrtation

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import com.kotlinapp.Login.LoginActivity
import com.kotlinapp.otp.OtpActivity
import com.kotlinapp.R
import com.kotlinapp.Utills.utills
import com.kotlinapp.retofit.ApiUtils
import com.kotlinapp.retofit.UserService
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom


class RegistrationActivity : Activity() {

    private var edtUserfirstname: EditText? = null
    private var edtUserlastname: EditText? = null
    private var edtUseremail: EditText? = null
    private var edtUsermobile: EditText? = null
    private var edtPassword: EditText? = null
    private var edtRepeatpassword: EditText? = null
    private var btnRegistration: Button? = null
    private var login_txt: TextView? = null
    private var edtUserdate: TextView? = null
    private var edtUsertime: TextView? = null
    var userService: UserService? = null
    var sharedpreferences: SharedPreferences? = null
    private val min = 0
    private val max = 50

    val mcurrentTime = Calendar.getInstance()
    val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
    val minute = mcurrentTime.get(Calendar.MINUTE)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        sharedpreferences =
            getSharedPreferences(utills.MyPREFERENCES, Context.MODE_PRIVATE)
        edtUserfirstname = findViewById<View>(R.id.edtUserfirstname) as EditText
        edtUserlastname = findViewById<View>(R.id.edtUserlastname) as EditText
        edtUseremail = findViewById<View>(R.id.edtUseremail) as EditText
        edtUsermobile = findViewById<View>(R.id.edtUsermobile) as EditText
        edtPassword = findViewById<View>(R.id.edtPassword) as EditText
        edtRepeatpassword = findViewById<View>(R.id.edtRepeatpassword) as EditText
        login_txt = findViewById(R.id.login_txt)
        edtUserdate = findViewById(R.id.edtUserdate)
        edtUsertime = findViewById(R.id.edtUsertime)
        btnRegistration =
            findViewById<View>(R.id.btnRegistration) as Button
        userService = ApiUtils.userService
        btnRegistration!!.setOnClickListener {
            val Userfirstname = edtUserfirstname!!.text.toString()
            val Userlastname = edtUserlastname!!.text.toString()
            val Useremail = edtUseremail!!.text.toString()
            val Useremobile = edtUsermobile!!.text.toString()
            val Userdob = edtUserdate!!.text.toString()
            val Useretime = edtUsertime!!.text.toString()
            val password = edtPassword!!.text.toString()
            val Repeatpassword = edtRepeatpassword!!.text.toString()
            //validate form
            if (validateLogin(
                    Userfirstname,
                    Userlastname,
                    Useremail,
                    Useremobile,
                    Userdob,
                    Useretime,
                    password,
                    Repeatpassword
                )
            ) {
                val editor = sharedpreferences!!.edit()
                val random = ThreadLocalRandom.current().nextInt(min, max)
                editor.putString(utills.ID, random.toString())
                editor.putString(utills.F_Name, Userfirstname);
                editor.putString(utills.L_Name, Userlastname);
                editor.putString(utills.Phone, Useremobile)
                editor.putString(utills.Email, Useremail)
                editor.putString(utills.DOB, Userdob)
                editor.putString(utills.Time, Useretime)
                editor.commit()
                //do login
                //                    doLogin(username, password);
                val intent = Intent(
                    this@RegistrationActivity,
                    OtpActivity::class.java
                )
                startActivity(intent)
                finish()
            }
        }
        login_txt!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@RegistrationActivity,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish()
        })

        val mTimePicker: TimePickerDialog

        mTimePicker = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                var hourOfDay = hourOfDay
                var am_pm = ""
                // AM_PM decider logic
                when {hourOfDay == 0 -> { hourOfDay += 12
                    am_pm = "AM"
                }
                    hourOfDay == 12 -> am_pm = "PM"
                    hourOfDay > 12 -> { hourOfDay -= 12
                        am_pm = "PM"
                    }
                    else -> am_pm = "AM"
                }
                if (edtUsertime != null) {
                    val hourOfDay = if (hour < 10) "0" + hourOfDay else hourOfDay
                    val min = if (minute < 10) "0" + minute else minute
                    // display format of time
                    val msg = "$hourOfDay : $min $am_pm"
//                    edtUserdate!!.setText(String.format("%d : %d  %d", hourOfDay, min, am_pm))
                    edtUsertime!!.text = msg
                }
            }, hour, minute, false)

        edtUsertime!!.setOnClickListener {
            mTimePicker.show()
//            OnClickTime()
        }

        edtUserdate!!.setOnClickListener {
            DatePickerDialog(this@RegistrationActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                mcurrentTime.get(Calendar.YEAR),
                mcurrentTime.get(Calendar.MONTH),
                mcurrentTime.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    // create an OnDateSetListener
    val dateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            mcurrentTime.set(Calendar.YEAR, year)
            mcurrentTime.set(Calendar.MONTH, monthOfYear)
            mcurrentTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edtUserdate!!.text = sdf.format(mcurrentTime.getTime())
    }

    private fun OnClickTime() {
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            if (edtUserdate != null) {
                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "Time is: $hour : $min $am_pm"
                edtUserdate!!.text = msg
                edtUserdate!!.visibility = ViewGroup.VISIBLE
            }
        }
    }

    private fun validateLogin(
        userfirstname: String?,
        userlastname: String?,
        useremail: String?,
        useremobile: String?,
        Userdob: String?,
        Useretime: String?,
        password: String?,
        Repeatpassword: String?
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
        if (Userdob == null || Userdob.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "User DOB is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (Useretime == null || Useretime.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "User time is required", Toast.LENGTH_SHORT).show()
            return false
        }
        /*if (useremobile.matches(MobilePattern)) {
            Toast.makeText(this, "Please enter valid mobiles no.", Toast.LENGTH_SHORT).show();
            return false;
        }*/if (password == null || password.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 8) {
            Toast.makeText(
                this,
                "User enter 8 or more than 8 character password.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (Repeatpassword == null || Repeatpassword.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "Repeat password is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (Repeatpassword.length < 8) {
            Toast.makeText(
                this,
                "User enter 8 or more than 8 character confirm password.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (password != Repeatpassword) {
            Toast.makeText(this, "Confirm password not matched", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onBackPressed() { //        super.onBackPressed();
        val intent = Intent(
            this@RegistrationActivity,
            LoginActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}