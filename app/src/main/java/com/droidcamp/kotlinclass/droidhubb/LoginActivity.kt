package com.droidcamp.kotlinclass.droidhubb

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.droidcamp.kotlinclass.droidhubb.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }


        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {

        if (binding.emailAddressEt.text!!.isEmpty()) {
            binding.emailAddressEt.error = "Please enter your email address"
            binding.emailAddressEt.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailAddressEt.text.toString()).matches()) {
            binding.emailAddressEt.error = "not a valid email"
            binding.emailAddressEt.requestFocus()
            return
        }

        if (binding.passwordEt.text!!.isEmpty()) {
            binding.passwordEt.error = "Please enter your password"
            binding.passwordEt.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(binding.emailAddressEt.text.toString(), binding.passwordEt.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val user = auth.currentUser
                        updateUI(user)
                    } else {

                        updateUI(null)
                    }

                }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

   private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, FilesActivity::class.java))
                finish()
            }else{
                Toast.makeText(
                        baseContext, "Please verify your email address.",
                        Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                    baseContext, "Login failed.",
                    Toast.LENGTH_SHORT
            ).show()
        }

    }
}