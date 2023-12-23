package com.example.main

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.main.Login.LoginActivity
import com.example.main.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.annotations.NonNull
import java.util.Objects

class MainActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        val button = findViewById<Button>(R.id.logout)
        val textView = findViewById<TextView>(R.id.userdetails)
        val user =auth.currentUser

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
            finish()
        } else {
            textView.text = user.email
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    fun addToFavourite(context: Context, title:String, overview:String, poster:ImageView) {
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        var timeStamp: Long = System.currentTimeMillis()

        val hashMap: HashMap<String, Any> = HashMap()
        hashMap.put("title", "" + title)
        hashMap.put("overview", "" + overview)
        hashMap.put("poster", "" + poster)

        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid.toString()).child("Favourites")
            .child(title)
            .child(overview)
            .child(poster.toString())
            .setValue(hashMap)
            .addOnSuccessListener {
                Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Could not Add to Favourites", Toast.LENGTH_SHORT).show()
            }

        fun removeFromFavourite(context: Context, title:String, overview:String, poster:ImageView) {
            val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
            var timeStamp: Long = System.currentTimeMillis()

            val hashMap: HashMap<String, Any> = HashMap<String, Any>()
            hashMap.put("title", "" + title)
            hashMap.put("overview", "" + overview)
            hashMap.put("poster", "" + poster)

            val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
            ref.child(firebaseAuth.uid.toString()).child("Favourites")
                .child(title)
                .child(overview)
                .child(poster.toString())
                .removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Removed from favourites", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Could not Remove from Favourites", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
}
