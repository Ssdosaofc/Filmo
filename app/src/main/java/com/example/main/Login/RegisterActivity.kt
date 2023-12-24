

package com.example.main.Login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.main.MainActivity2
import com.example.main.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    //private lateinit var firebaseDatabase: FirebaseDatabase
    //private lateinit var root: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth= FirebaseAuth.getInstance()
        val editTextEmail = findViewById<TextInputEditText>(R.id.email)
        val editTextPassword = findViewById<TextInputEditText>(R.id.password)
        val editTextVerifyPassword = findViewById<TextInputEditText>(R.id.verifypassword)
        val buttonReg = findViewById<Button>(R.id.reg)
        val textView = findViewById<TextView>(R.id.loginNow)
        val progressBar = findViewById<ProgressBar>(R.id.progressbarR)
        //db = FirebaseDatabase.getInstance()
        //root = db.reference.child("Users")

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
                progressBar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
/*
                        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
                        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

                        val hashMap: HashMap<String, Any> = HashMap()
                        hashMap.put("email",""+email)
                        hashMap.put("password",""+password)

                        ref.child(firebaseAuth.uid.toString()).child("users")
                            .setValue(hashMap)
*/
                        Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity2::class.java)
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
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
}
