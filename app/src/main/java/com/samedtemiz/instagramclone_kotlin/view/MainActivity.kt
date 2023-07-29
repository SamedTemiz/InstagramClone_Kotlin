package com.samedtemiz.instagramclone_kotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samedtemiz.instagramclone_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Firebase
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun login(view: View) {
        //Login
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "Enter email and password!", Toast.LENGTH_SHORT).show()
        } else {

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    //Success
                    val intent = Intent(this@MainActivity, FeedActivity::class.java)
                    startActivity(intent)
                    finish()

                }.addOnFailureListener {
                    //Failure
                    Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }

    }

    fun register(view: View) {
        //Register
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "Enter email and password!", Toast.LENGTH_SHORT).show()
        } else {

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    //Success

                    val intent = Intent(this@MainActivity, FeedActivity::class.java)
                    startActivity(intent)
                    finish()

                }.addOnFailureListener {
                    //Failure
                    Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }

        }
    }

}