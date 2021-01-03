package com.droidcamp.kotlinclass.droidhubb

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.droidcamp.kotlinclass.droidhubb.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var name: EditText
    private lateinit var emailAddress: EditText
    private lateinit var password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_DroidHubb)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }

        name = binding.nameEt
        emailAddress = binding.emailAddressEt
        password = binding.passwordEt

        binding.btnSignUp.setOnClickListener {
            signUpUser()

        }
    }

    private fun signUpUser() {
        if (name.text.isEmpty()) {
            name.error = "Please enter your name"
            name.requestFocus()
            return
        }
        if (emailAddress.text.isEmpty()) {
            emailAddress.error = "Please enter your email address"
            emailAddress.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress.text.toString()).matches()) {
            emailAddress.error = "not a valid email"
        }

        if (password.text.isEmpty()) {
            password.error = "Please enter your password"
            password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(emailAddress.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(
                        baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}