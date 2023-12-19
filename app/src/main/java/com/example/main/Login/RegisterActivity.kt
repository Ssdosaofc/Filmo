

package com.example.main.Login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.main.MainActivity
import com.example.main.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var root: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth= FirebaseAuth.getInstance()
        val editTextEmail = findViewById<TextInputEditText>(R.id.email)
        val editTextPassword = findViewById<TextInputEditText>(R.id.password)
        val editTextVerifyPassword = findViewById<TextInputEditText>(R.id.verifypassword)
        val buttonReg = findViewById<Button>(R.id.reg)
        val textView = findViewById<TextView>(R.id.loginNow)
        db = FirebaseDatabase.getInstance()
        root = db.getReference().child("Users")

        textView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonReg.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val verifypassword = editTextVerifyPassword.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()

            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(verifypassword)) {
                Toast.makeText(this, "Verify Password", Toast.LENGTH_SHORT).show()

            } else if (verifypassword != password) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()

            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userMap = HashMap<String, String>()
                        userMap["email"] = email
                        userMap["password"] = password

                        root.setValue(userMap)

                        Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }


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
