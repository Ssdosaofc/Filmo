package com.example.main

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var editTextEmail = findViewById<TextInputEditText>(R.id.email)
    var editTextPassword = findViewById<TextInputEditText>(R.id.password)
    var editTextVerifyPassword = findViewById<TextView>(R.id.verifypassword)
    var buttonReg = findViewById<Button>(R.id.reg)

    var textView = findViewById<TextView>(R.id.loginNow)
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth= FirebaseAuth.getInstance()

        textView.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

        buttonReg.setOnClickListener(View.OnClickListener {
            val email = editTextEmail.toString()
            val password = editTextPassword.toString()
            val verifypassword = editTextVerifyPassword.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(verifypassword)) {
                Toast.makeText(this, "Verify Password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (verifypassword != password) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(OnCompleteListener {task->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this, "Account created",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        })



    }
}