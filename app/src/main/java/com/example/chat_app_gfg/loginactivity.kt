package com.example.chat_app_gfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class loginactivity : AppCompatActivity() {
    private lateinit var login_username : EditText
    private lateinit var  login_password : EditText
    private lateinit var login_button : Button
    private lateinit var signupredirectext : TextView
    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginactivity)
        supportActionBar?.hide()
         mAuth = FirebaseAuth.getInstance()
        login_button = findViewById(R.id.login_button)
        login_password = findViewById(R.id.login_password)
        login_username = findViewById(R.id.login_username)
        signupredirectext = findViewById(R.id.signupRedirectText)
        signupredirectext.setOnClickListener {
            val intent = Intent(this,signupactivity::class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener(View.OnClickListener {
            val email = login_username.text.toString()
            val password = login_password.text.toString()
            login(email,password)
        })
    }
    private fun login(email:String , password : String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@loginactivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@loginactivity,"user does not exist",Toast.LENGTH_SHORT).show()
                }
            }
    }

}