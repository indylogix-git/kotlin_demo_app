package com.kotlinapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kotlinapp.Utills.utills.isConnected
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_text.*
import java.util.*

class TextSpeachActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var editText: EditText? = null

    private var header_name: TextView? = null
    private var back_arrow: ImageView? = null
    private var menu_icon: ImageView? = null

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        back_arrow = findViewById(R.id.back_arrow)
        menu_icon = findViewById(R.id.menu_icon)
        header_name = findViewById(R.id.header_name)

        header_name!!.setText(R.string.title_forgot)

        buttonSpeak = this.button_speak
        editText = this.edittext_input

        buttonSpeak!!.isEnabled = false;
        tts = TextToSpeech(this, this)

        buttonSpeak!!.setOnClickListener {
            val snack = Snackbar.make(it, "This is a simple Snackbar", Snackbar.LENGTH_LONG)
            snack.setAction("DISMISS", View.OnClickListener {
                // executed when DISMISS is clicked
                System.out.println("Snackbar Set Action - OnClick.")
                speakOut()
            })

            // change action button text color
            snack.setActionTextColor(Color.parseColor("#BB4444"))

            // snackbar background color
            snack.view.setBackgroundColor(Color.parseColor("#FFFFFF"))

            val textView = snack.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            // change Snackbar text color
            textView.setTextColor(Color.parseColor("#4444DD"))

            snack.show()
        }

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        //crating an arraylist to store users using the data class user
        val users = ArrayList<User>()

        //adding some dummy data to the list
        users.add(User("Belal Khan", "Ranchi Jharkhand"))
        users.add(User("Ramiz Khan", "Ranchi Jharkhand"))
        users.add(User("Faiz Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))
        users.add(User("Yashar Khan", "Ranchi Jharkhand"))

        if (isConnected(this)) {
            //creating our adapter
            val adapter = CustomAdapter(users)

            //now adding the adapter to recyclerview
            recyclerView.adapter = adapter
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_connection, Toast.LENGTH_SHORT).show();
        }

    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun speakOut() {
        val text = editText!!.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}
