package com.kotlinapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.e.myapplication.ui.gallery.GalleryFragment
import com.e.myapplication.ui.home.HomeFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.kotlinapp.Utills.utills
import com.kotlinapp.Utills.utills.isConnected
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var toolbar: Toolbar? = null
    private var profile_view: LinearLayout? = null
    private var imageView: ImageView? = null
    private var user_name: TextView? = null
    private var user_email: TextView? = null
    private var user_mobile: TextView? = null
    private var fab: FloatingActionButton? = null
    private var header: View? = null
    private var navigationView: NavigationView? = null
    var sharedpreferences: SharedPreferences? = null

    @SuppressLint("WrongConstant", "ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedpreferences =
            getSharedPreferences(utills.MyPREFERENCES, Context.MODE_PRIVATE)

        navigationView = findViewById(R.id.nav_view) as NavigationView
        header = navigationView!!.getHeaderView(0)

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        fab = findViewById<FloatingActionButton>(R.id.fab)
        imageView = header!!.findViewById<ImageView>(R.id.imageView)
        user_name = header!!.findViewById<TextView>(R.id.user_name)
        user_email = header!!.findViewById<TextView>(R.id.user_email)
        user_mobile = header!!.findViewById<TextView>(R.id.user_mobile)
        profile_view = header!!.findViewById<LinearLayout>(R.id.profile_view)

        toolbar!!.setTitle(R.string.app_name)
        user_name!!.text = "Name : "+sharedpreferences?.getString(utills.F_Name, "")+" "+sharedpreferences?.getString(utills.L_Name, "")
        this.user_email!!.text = "Email : "+sharedpreferences!!.getString(utills.Email, "")
        this.user_mobile!!.text = "Mobile : "+sharedpreferences!!.getString(utills.Phone, "")

        fab!!.setBackgroundColor(R.color.colorPrimary)
        fab!!.setOnClickListener { Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show() }

        fab!!.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this@MainActivity)

        if (isConnected(this)) {
            Toast.makeText(applicationContext, "Connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, R.string.error_connection, Toast.LENGTH_SHORT).show()
        }

        profile_view!!.setOnClickListener { val intent = Intent(
            this@MainActivity,
            ProfileActivity::class.java
        )
            startActivity(intent)
            finish() }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.nav_logout -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
//                replaceFragmenty(
//                    fragment = HomeFragment(),
//                    allowStateLoss = true,
//                    containerViewId = R.id.mainContent
//                )
                setTitle("Import")
            }
            R.id.nav_gallery -> {
//                replaceFragmenty(
//                    fragment = GalleryFragment(),
//                    allowStateLoss = true,
//                    containerViewId = R.id.mainContent
//                )
                setTitle("Gallery")
            }
            R.id.nav_slideshow -> {
//                replaceFragmenty(
//                    fragment = Fragment3(),
//                    allowStateLoss = true,
//                    containerViewId = R.id.mainContent
//                )
                setTitle("Slideshow")
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
