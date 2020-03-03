package com.kotlinapp.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.kotlinapp.Animation.MyBounceInterpolator
import com.kotlinapp.Login.LoginActivity
import com.kotlinapp.R
import com.kotlinapp.Utills.utills
import com.kotlinapp.Utills.utills.isConnected
import com.kotlinapp.adpters.TabPagerAdapter
import com.kotlinapp.fragments.*
import com.kotlinapp.profile.ProfileActivity
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

    private var tabLayout: TabLayout? = null
    private var pager: ViewPager? = null
    private var value: String? = null

    private val fragmentManager = supportFragmentManager
    private val firstFragment = FirstFragment()
    private val secondFragment = SecondFragment()
    private val thirdFragment = ThirdFragment()
    private val fourthFragment = FourthFragment()
    private val fifthFragment = FifthFragment()
    private val sixthFragment = SixthFragment()
    private val sevenFragment = SevenFragment()
    private val eightFragment = EightFragment()

    val lable = listOf("First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Eight")

    @SuppressLint("WrongConstant", "ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedpreferences =
            getSharedPreferences(utills.MyPREFERENCES, Context.MODE_PRIVATE)

        navigationView = findViewById(R.id.nav_view) as NavigationView
        header = navigationView!!.getHeaderView(0)

        tabLayout = findViewById(R.id.tabLayout)
        pager = findViewById(R.id.pager)

        /* Display First Fragment initially */
        loadFragment(FirstFragment())
        /*val bundle = Bundle()
        bundle.putString("TITLE", value)
        val fragmentManager = supportFragmentManager
        firstFragment.arguments = bundle
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, firstFragment)
        fragmentTransaction.commit()*/

        for(i in lable.indices){
            tabLayout!!.addTab(tabLayout!!.newTab().setText(lable[i]))
        }

        val adapter = TabPagerAdapter(
            supportFragmentManager,
            tabLayout!!.tabCount
        )
        pager!!.adapter = adapter

        pager!!.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager!!.currentItem = tab.position

                value = tab.position.toString()

                if (tab.position == 0) {
                    loadFragment(FirstFragment())
                    title = "First"
                } else if (tab.position == 1) {
                    val bundle = Bundle()
                    bundle.putString("TITLE", value)
                    val fragmentManager = supportFragmentManager
                    secondFragment.arguments = bundle
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, secondFragment)
                    fragmentTransaction.commit()
                    title = "Second"
                } else if (tab.position == 2) {
                    val bundle = Bundle()
                    bundle.putString("TITLE", value)
                    val fragmentManager = supportFragmentManager
                    thirdFragment.arguments = bundle
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, thirdFragment)
                    fragmentTransaction.commit()
                    title = "Third"
                } else if (tab.position == 3) {
                    val bundle = Bundle()
                    bundle.putString("TITLE", value)
                    val fragmentManager = supportFragmentManager
                    fourthFragment.arguments = bundle
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, fourthFragment)
                    fragmentTransaction.commit()
                    title = "Fourth"
                } else if (tab.position == 4) {
                    val bundle = Bundle()
                    bundle.putString("TITLE", value)
                    val fragmentManager = supportFragmentManager
                    fifthFragment.arguments = bundle
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, fifthFragment)
                    fragmentTransaction.commit()
                    title = "Fifth"
                } else if (tab.position == 5) {
                    val bundle = Bundle()
                    bundle.putString("TITLE", value)
                    val fragmentManager = supportFragmentManager
                    sixthFragment.arguments = bundle
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, sixthFragment)
                    fragmentTransaction.commit()
                    title = "Sixth"
                } else if (tab.position == 6) {
                    val bundle = Bundle()
                    bundle.putString("TITLE", value)
                    val fragmentManager = supportFragmentManager
                    sevenFragment.arguments = bundle
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, sevenFragment)
                    fragmentTransaction.commit()
                    title = "Seven"
                } else if (tab.position == 7) {
                    val bundle = Bundle()
                    bundle.putString("TITLE", value)
                    val fragmentManager = supportFragmentManager
                    eightFragment.arguments = bundle
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, eightFragment)
                    fragmentTransaction.commit()
                    title = "Eight"
                }

//              loadFragment(new ChooseWorkFragment());
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        fab = findViewById<FloatingActionButton>(R.id.fab)
        imageView = header!!.findViewById<ImageView>(R.id.imageView)
        user_name = header!!.findViewById<TextView>(R.id.user_name)
        user_email = header!!.findViewById<TextView>(R.id.user_email)
        user_mobile = header!!.findViewById<TextView>(R.id.user_mobile)
        profile_view = header!!.findViewById<LinearLayout>(R.id.profile_view)

        toolbar!!.setTitle(R.string.app_name)
        user_name!!.text = "Name : " + sharedpreferences?.getString(
            utills.F_Name,
            ""
        ) + " " + sharedpreferences?.getString(utills.L_Name, "")
        this.user_email!!.text = "Email : " + sharedpreferences!!.getString(utills.Email, "")
        this.user_mobile!!.text = "Mobile : " + sharedpreferences!!.getString(utills.Phone, "")

        val myAnim =
            AnimationUtils.loadAnimation(this, R.anim.bounce_animation)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator

        fab!!.startAnimation(myAnim)

        fab!!.setOnClickListener {
            checkPermission()
        }

        /*fab!!.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this@MainActivity)

        if (isConnected(this)) {
            Toast.makeText(applicationContext, "Connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext,
                R.string.error_connection, Toast.LENGTH_SHORT).show()
        }

        profile_view!!.setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                ProfileActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CALL_PHONE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42
                )
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun callPhone(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9829194215"))
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        startActivity(intent)
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
                loadFragment(FirstFragment())
                title = "Home"
            }
            R.id.nav_gallery -> {
                val bundle = Bundle()
                bundle.putString("fragment", "Gallery")
                val fragment: Fragment = GalleryFragment()
                val fragmentManager =
                    supportFragmentManager
                fragment.arguments = bundle
                val fragmentTransaction =
                    fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                title = "Gallery"
            }
            R.id.nav_slideshow -> {
                val bundle = Bundle()
                bundle.putString("fragment", "Slideshow")
                val fragment: Fragment = GalleryFragment()
                val fragmentManager =
                    supportFragmentManager
                fragment.arguments = bundle
                val fragmentTransaction =
                    fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                title = "Slideshow"
            }
            R.id.nav_logout -> {
                val editor = sharedpreferences!!.edit()
                editor.putString(utills.ID, "")
                editor.putString(utills.Name, "")
                editor.putString(utills.Phone, "")
                editor.putString(utills.Email, "")
                editor.putString(utills.F_Name, "")
                editor.putString(utills.L_Name, "")
                editor.putString(utills.DOB, "")
                editor.putString(utills.Time, "")
                editor.commit()
                val logout = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(logout)
                finishAffinity()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadFragment(fragment: Fragment) { // load fragment
        val bundle = Bundle()
        bundle.putString("TITLE", value)
        val fragmentManager = supportFragmentManager
        firstFragment.arguments = bundle
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
