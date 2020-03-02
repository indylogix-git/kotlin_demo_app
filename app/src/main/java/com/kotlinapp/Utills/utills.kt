package com.kotlinapp.Utills

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.util.regex.Pattern


object utills {
    var connectivityManager: ConnectivityManager? = null
    const val MyPREFERENCES = "Immigration"
    const val ID = "Id"
    const val F_Name = "F_Name"
    const val L_Name = "L_Name"
    const val Name = "nameKey"
    const val Phone = "phoneKey"
    const val Email = "emailKey"
    var EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9+._%-+]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                "(" +
                "." +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                ")+"
    )
    const val Wrong_word = ""
    fun isConnected(context: Context): Boolean {
        var connected = false
        try {
            val cm =
                context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message)
        }
        return connected
    }
}