package com.example.chat_app_gfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signupactivity : AppCompatActivity() {
    private lateinit var signup_name : EditText
    private lateinit var  signup_email : EditText
    private lateinit var  signup_password : EditText
    private lateinit var  signup_username : EditText
    private lateinit var signup_button : Button
    private lateinit var loginredirectext : TextView
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mdb:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupactivity)
        supportActionBar?.hide()
        signup_name = findViewById(R.id.signup_name)
        signup_email = findViewById(R.id.signup_email)
        signup_button = findViewById(R.id.signup_button)
        loginredirectext = findViewById(R.id.loginRedirectText)
        signup_password = findViewById(R.id.signup_password)
        signup_username = findViewById(R.id.signup_username)
        mAuth = FirebaseAuth.getInstance()
        loginredirectext.setOnClickListener {
            val intent = Intent(this,loginactivity::class.java)
            startActivity(intent)
        }
        signup_button.setOnClickListener {
            val name = signup_username.text.toString()
            val email = signup_email.text.toString()
            val password = signup_password.text.toString()
            signup(name,email,password)
        }


    }
    private fun signup(name:String,email:String , password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUsertoDatabase(name,email, mAuth.currentUser!!.uid!!)
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@signupactivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@signupactivity,"some error occured",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUsertoDatabase(name:String,email:String,uid:String){
        mdb = FirebaseDatabase.getInstance().getReference()
        mdb.child("user").child(uid).setValue(user(name,email,uid))

    }

}