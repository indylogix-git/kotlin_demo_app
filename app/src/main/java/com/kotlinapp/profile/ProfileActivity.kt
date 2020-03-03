package com.kotlinapp.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.text.SimpleDateFormat
import java.util.*


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
    private var edtUserdob: TextView? = null
    private var edtUsertime: TextView? = null

    val mcurrentTime = Calendar.getInstance()
    val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
    val minute = mcurrentTime.get(Calendar.MINUTE)

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
        edtUserdob = findViewById<View>(R.id.edtUserdob) as TextView
        edtUsertime = findViewById<View>(R.id.edtUsertime) as TextView

        back_arrow!!.isVisible = true
        header_name!!.setText(R.string.title_profile)

        edtUserfirstname!!.setText(sharedpreferences!!.getString(utills.F_Name, ""))
        edtUserlastname!!.setText(sharedpreferences!!.getString(utills.L_Name, ""))
        edtUseremail!!.setText(sharedpreferences!!.getString(utills.Email, ""))
        edtUsermobile!!.setText(sharedpreferences!!.getString(utills.Phone, ""))
        edtUserdob!!.setText(sharedpreferences!!.getString(utills.DOB, ""))
        edtUsertime!!.setText(sharedpreferences!!.getString(utills.Time, ""))

        btnUpdate =
            findViewById<View>(R.id.btnUpdate) as Button
        userService = ApiUtils.userService
        btnUpdate!!.setOnClickListener {
            val Userfirstname = edtUserfirstname!!.text.toString()
            val Userlastname = edtUserlastname!!.text.toString()
            val Useremail = edtUseremail!!.text.toString()
            val Useremobile = edtUsermobile!!.text.toString()
            val Useredob = edtUserdob!!.text.toString()
            val Useretime = edtUsertime!!.text.toString()
            //validate form
            if (validateLogin(
                    Userfirstname,
                    Userlastname,
                    Useremail,
                    Useremobile,
                    Useredob,
                    Useretime
                )
            ) {
                val editor = sharedpreferences!!.edit()
                val random = sharedpreferences!!.getString(utills.ID, "")
                editor.putString(utills.ID, random.toString())
                editor.putString(utills.F_Name, Userfirstname);
                editor.putString(utills.L_Name, Userlastname);
                editor.putString(utills.Phone, Useremobile)
                editor.putString(utills.Email, Useremail)
                editor.putString(utills.DOB, Useredob)
                editor.putString(utills.Time, Useretime)
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

        edtUserdob!!.setOnClickListener {
            DatePickerDialog(this@ProfileActivity,
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
        edtUserdob!!.text = sdf.format(mcurrentTime.getTime())
    }

    private fun validateLogin(
        userfirstname: String?,
        userlastname: String?,
        useremail: String?,
        useremobile: String?,
        Useredob: String?,
        Useretime: String?
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
        if (Useredob == null || Useredob.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "User DOB is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (Useretime == null || Useretime.trim { it <= ' ' }.length == 0) {
            Toast.makeText(this, "User time is required", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}