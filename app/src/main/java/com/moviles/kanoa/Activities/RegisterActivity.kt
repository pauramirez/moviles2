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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.moviles.kanoa.R
import com.google.firebase.auth.UserProfileChangeRequest
import android.support.annotation.NonNull
import com.firebase.ui.database.FirebaseListAdapter


class RegisterActivity : AppCompatActivity() {

    private lateinit  var txtName: EditText
    private lateinit  var txtLastName:EditText
    private lateinit  var txtEmail:EditText
    private lateinit  var txtPassword:EditText
    private lateinit  var progressBar: ProgressBar
    private lateinit  var dbReference:DatabaseReference
    private lateinit  var database:FirebaseDatabase
    private lateinit  var auth:FirebaseAuth
    private lateinit  var mAuthListener:FirebaseAuth.AuthStateListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtName = findViewById(R.id.txtName)
        txtLastName = findViewById(R.id.txtLastName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        progressBar = findViewById(R.id.progressBarWWW)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference= database.reference.child("User")
    }

    fun register(view: View) {
        createNewAccount()
    }
    private fun createNewAccount(){
        val name:String = txtName.text.toString()
        val lastName: String = txtLastName.text.toString()
        val email: String = txtEmail.text.toString()
        val password: String = txtPassword.text.toString()

        if (!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(lastName)
                && !TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password))
        {
            progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                task->
                if(task.isComplete){
                    val user:FirebaseUser ?= auth.currentUser
                    verifyEmail(user)
                    val userDB = dbReference.child(user?.uid)
                    val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build()
                    user?.updateProfile(profileUpdates)

                    //mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                     //   if (user != null) {
                    //        val profileUpdates = UserProfileChangeRequest.Builder()
                     //               .setDisplayName(name).build()
                    //      user.updateProfile(profileUpdates)
                     //   }
                    //}
                    userDB.child("Name").setValue(name)
                    userDB.child("LastName").setValue(lastName)
                    action()
                }
            }
        }
    }

    //public override fun onResume() {
     //   super.onResume()
     //   auth.addAuthStateListener(mAuthListener)
    //}

    //public override fun onStop() {
     //   super.onStop()
     //   if (mAuthListener != null) {
     //       auth.removeAuthStateListener(mAuthListener)
     //   }
    //}

    private fun action(){
        startActivity(Intent(this, LoginActivity::class.java))
    }
    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()?.addOnCompleteListener(this){
            task->
            if(task.isComplete){
                Toast.makeText(this,"Email enviado",Toast.LENGTH_SHORT ).show()
            }else{
                Toast.makeText(this,"Error al registrar",Toast.LENGTH_SHORT ).show()
            }
        }
    }
}
