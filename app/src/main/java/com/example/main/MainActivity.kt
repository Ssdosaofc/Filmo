package com.example.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.main.Fragments.Home
import com.example.main.Fragments.Saved
import com.example.main.Login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth= FirebaseAuth.getInstance()
        val button = findViewById<Button>(R.id.logout)
        val textView = findViewById<TextView>(R.id.userdetails)
        val favButton = findViewById<Button>(R.id.Fav)
        val user =auth.currentUser

        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            textView.text = user.email

            val home = Home()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, home)
                .commit()

        }

        favButton.setOnClickListener{
            val saved = Saved()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, saved)
                .addToBackStack(null)
                .commit()

            favButton.setOnClickListener{
                val home = Home()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, home)
                    .commit()
            }
        }

        button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}