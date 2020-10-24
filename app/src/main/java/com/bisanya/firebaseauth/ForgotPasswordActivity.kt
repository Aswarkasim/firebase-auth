package com.bisanya.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        initActionBar()

        btnSendEmail.setOnClickListener {
            val email = etEmailForgotPassword.text.toString().trim()
            if(email.isEmpty()){
                etEmailForgotPassword.error = "Please field tour email"
                etEmailForgotPassword.requestFocus()
                return@setOnClickListener
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmailForgotPassword.error = "Please use valid email"
                etEmailForgotPassword.requestFocus()
                return@setOnClickListener
            }else{
                forgotPassword(email)
            }
        }

        tbForgotPassword.setNavigationOnClickListener {
            finish()
        }
    }

    private fun forgotPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Your reset password hasl been sent to your email", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SignInActivity::class.java))
                    finishAffinity()
                }else{
                    Toast.makeText(this, "Failed Reset Password", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun initActionBar() {
        setSupportActionBar(tbForgotPassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}