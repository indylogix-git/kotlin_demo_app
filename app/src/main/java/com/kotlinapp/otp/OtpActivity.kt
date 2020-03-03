package com.kotlinapp.otp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.kotlinapp.Login.LoginActivity
import com.kotlinapp.others.MySMSBroadcastReceiver
import com.kotlinapp.R
import com.kotlinapp.main.MainActivity


class OtpActivity : AppCompatActivity(),
    MySMSBroadcastReceiver.OTPReceiveListener {

    private var edtOtp_password: EditText? = null
    private var btnOtp: Button? = null
    private var smsReceiver: MySMSBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        edtOtp_password = findViewById<View>(R.id.edtOtp_password) as EditText
        btnOtp = findViewById<View>(R.id.btnotp) as Button

        btnOtp!!.setOnClickListener {
            val Forgot_password = edtOtp_password!!.text.toString()
                val intent = Intent(
                    this@OtpActivity,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
        }
    }

    /**
     * Starts SmsRetriever, which waits for ONE matching SMS message until timeout
     * (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
     * action SmsRetriever#SMS_RETRIEVED_ACTION.
     */
    private fun startSMSListener() {
        try {
            smsReceiver = MySMSBroadcastReceiver()
            smsReceiver!!.initOTPListener(this)

            val intentFilter = IntentFilter()
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
            this.registerReceiver(smsReceiver, intentFilter)

            val client = SmsRetriever.getClient(this)

            val task = client.startSmsRetriever()
            task.addOnSuccessListener {
                // API successfully started
                val intent = Intent(
                    this@OtpActivity,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
            }

            task.addOnFailureListener {
                // Fail to start API
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun onBtnResendClick(view: View){
        startSMSListener()
    }

    override fun onOTPReceived(otp: String) {
        //showToast("OTP Received: " + otp)
        edtOtp_password!!.setText(otp)
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver!!)
        }
    }

    override fun onOTPTimeOut() {
        showToast("OTP Time out")
    }


    override fun onDestroy() {
        super.onDestroy()
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver!!)
        }
    }
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() { //        super.onBackPressed();
        val intent = Intent(
            this@OtpActivity,
            LoginActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}