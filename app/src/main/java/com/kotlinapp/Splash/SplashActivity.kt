package com.kotlinapp.Splash

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.kotlinapp.Animation.MyBounceInterpolator
import com.kotlinapp.Login.LoginActivity
import com.kotlinapp.main.MainActivity
import com.kotlinapp.R
import com.kotlinapp.Utills.utills


class SplashActivity : Activity() {

    var handler: Handler? = null
    private val view: View? = null
    private var logo_img: ImageView? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        logo_img = findViewById(R.id.logo_img)

        val myAnim =
            AnimationUtils.loadAnimation(this, R.anim.bounce_animation)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = MyBounceInterpolator(0.2, 50.0)
        myAnim.interpolator = interpolator

        logo_img!!.startAnimation(myAnim)

        sharedpreferences =
            getSharedPreferences(utills.MyPREFERENCES, Context.MODE_PRIVATE)

        /*if (!checkPermission()) {
            requestPermission()
        } else {*/
            handler = Handler()
            handler!!.postDelayed({
                if (sharedpreferences!!.getString(utills.ID, "")!!.isEmpty()) {
                    val intent = Intent(
                        this@SplashActivity,
                        LoginActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(
                        this@SplashActivity,
                        MainActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }
            }, 1000)
            //            Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.CALL_PHONE
        )
        val result1 = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val result2 = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0) {
                val call = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val read = grantResults[1] == PackageManager.PERMISSION_GRANTED
                val write = grantResults[2] == PackageManager.PERMISSION_GRANTED
                if (call && read && write) { //                        Snackbar.make(view, "Permission Granted.", Snackbar.LENGTH_LONG).show();
                    handler = Handler()
                    handler!!.postDelayed({
                        if (sharedpreferences!!.getString(
                                utills.ID,
                                ""
                            )!!.isEmpty()
                        ) { //                                if (sharedpreferences.getString(utills.ID, null).equals("1")) {
                            val intent = Intent(
                                this@SplashActivity,
                                LoginActivity::class.java
                            )
                            startActivity(intent)
                            finish()
                        } else {
                            val intent = Intent(
                                this@SplashActivity,
                                MainActivity::class.java
                            )
                            startActivity(intent)
                            finish()
                        }
                    }, 1000)
                } else { //                        Snackbar.make(view, "Permission Denied.", Snackbar.LENGTH_LONG).show();
                    val _permission = permissions[0]
                    val _permission1 = permissions[1]
                    val _permission2 = permissions[2]
                    val showRationale =
                        shouldShowRequestPermissionRationale(_permission)
                    val showRationale1 =
                        shouldShowRequestPermissionRationale(_permission1)
                    val showRationale2 =
                        shouldShowRequestPermissionRationale(_permission2)
                    if (!showRationale && !showRationale1 && !showRationale2) {
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                            showMessageOKCancel("You need to allow access to all the permissions",
                                DialogInterface.OnClickListener { dialog, which ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(
                                            arrayOf(
                                                Manifest.permission.CALL_PHONE,
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            ),
                                            PERMISSION_REQUEST_CODE
                                        )
                                    }
                                })
                            return
                        }
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showMessageOKCancel("You need to allow access to all the permissions",
                                DialogInterface.OnClickListener { dialog, which ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(
                                            arrayOf(
                                                Manifest.permission.CALL_PHONE,
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            ),
                                            PERMISSION_REQUEST_CODE
                                        )
                                    }
                                })
                            return
                        }
                        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            showMessageOKCancel("You need to allow access to all the permissions",
                                DialogInterface.OnClickListener { dialog, which ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(
                                            arrayOf(
                                                Manifest.permission.CALL_PHONE,
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            ),
                                            PERMISSION_REQUEST_CODE
                                        )
                                    }
                                })
                            return
                        }
                    } else {
                        Snackbar.make(view!!, "Permission Denied.", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(
        message: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(this@SplashActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton(
                "Cancel"
            ) { dialog, which -> finish() }
            .create()
            .show()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 200
    }
}