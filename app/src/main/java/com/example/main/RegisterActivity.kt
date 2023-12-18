@file:Suppress("IMPLICIT_CAST_TO_ANY")

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth= FirebaseAuth.getInstance()
        val editTextEmail = findViewById<TextInputEditText>(R.id.email)
        val editTextPassword = findViewById<TextInputEditText>(R.id.password)
        val editTextVerifyPassword = findViewById<TextInputEditText>(R.id.verifypassword)
        val buttonReg = findViewById<Button>(R.id.reg)
        val textView = findViewById<TextView>(R.id.loginNow)

        textView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonReg.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val verifypassword = editTextVerifyPassword.text.toString()
            val name =
            if (email.isEmpty()) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()

            } else if (password.isEmpty()) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            } else if (verifypassword.isEmpty()) {
                Toast.makeText(this, "Verify Password", Toast.LENGTH_SHORT).show()

            } else if (verifypassword != password) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()

            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        showToast("Account created")
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        showToast("Authentication failed.")
                    }
                }
            }


        }


    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
