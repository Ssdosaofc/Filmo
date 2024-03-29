package com.example.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.NightMode
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.main.Login.LoginActivity
import com.example.main.Recycler.ViewAdapter
import com.example.main.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.view.View
import java.util.UUID

class MainActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMain2Binding

    private lateinit var editor:SharedPreferences.Editor

    var nightMode: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        auth= FirebaseAuth.getInstance()
        val button = findViewById<Button>(R.id.logout)
        val textView = findViewById<TextView>(R.id.userdetails)
        //val progressBar: ProgressBar = findViewById(R.id.progress)
        val user =auth.currentUser

        supportActionBar?.hide()
        val switchCompat:SwitchCompat = findViewById(R.id.switchCompat)
        val sharedPreferences:SharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val nightMode = sharedPreferences.getBoolean("night", false)

        if (nightMode){
            switchCompat.isChecked
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        switchCompat.setOnClickListener(object : OnClickListener {
            override fun onClick(v: android.view.View?) {
                if (nightMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    editor = sharedPreferences.edit()
                    editor.putBoolean("night", false)
                } else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    editor = sharedPreferences.edit()
                    editor.putBoolean("night", true)
                }
                editor.apply()

            }

        })

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        auth= FirebaseAuth.getInstance()

        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //finish()
        } else {
            textView.text = user.email
        }



        button.setOnClickListener {
            //progressBar.visibility = android.view.View.VISIBLE
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //finish()
            //progressBar.visibility = android.view.View.GONE
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }


}

