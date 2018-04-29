package com.moviles.kanoa.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.moviles.kanoa.R

private lateinit  var auth: FirebaseAuth
private lateinit  var progressBar: ProgressBar


class ForgotPassActivity : AppCompatActivity() {

    private lateinit  var txtEmail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        txtEmail = findViewById(R.id.txtEmail)
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBarWWW)
    }
    fun send(view:View){
        val email = txtEmail.text.toString()
        if(!TextUtils.isEmpty(email))
        {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(this){
                task ->
                if(task.isSuccessful)
                {
                    progressBar.visibility=View.VISIBLE
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                else
                {
                    Toast.makeText(this,"Error al enviar el email", Toast.LENGTH_LONG ).show()
                }
            }
        }
    }
}
