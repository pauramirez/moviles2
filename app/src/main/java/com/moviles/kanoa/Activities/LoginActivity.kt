@file:Suppress("DEPRECATION")

package com.moviles.kanoa.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import android.text.Editable
import android.text.TextWatcher
import com.moviles.kanoa.R
import android.widget.*
import com.google.firebase.auth.FirebaseUser
import java.io.File


@Suppress("UNREACHABLE_CODE")
class LoginActivity : AppCompatActivity(), TextWatcher {

    private lateinit  var txtUser: EditText
    private lateinit  var txtPassword: EditText
    private lateinit  var login: Button
    private lateinit  var progressBar: ProgressBar
    private lateinit  var auth: FirebaseAuth
    private var TAG :String ="LOGIN"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        session = new Session(cntx); //in oncreate
        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)
        progressBar = findViewById(R.id.progressBarWWW)
        login = findViewById(R.id.loginBtn)
        auth = FirebaseAuth.getInstance()

        txtPassword?.addTextChangedListener(this)
        txtUser?.addTextChangedListener(this)

        val userActive = FirebaseAuth.getInstance().currentUser
        if (userActive != null) {
            // User is signed in so call the home view
            action()
        }

        //val file = File(context.filesDir, "miniMongo")

    }

    fun checkNetwork(): Boolean {
        var hayE = false
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            hayE = true
            Toast.makeText(this, "Maps is ready to be used!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Oops! It seems you dont have network connection :( so sorry!", Toast.LENGTH_SHORT).show()
            hayE = false
        }
        return hayE
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (txtUser.text.isBlank()|| txtPassword.text.isBlank())
        {
            login.isClickable = false
            login.isEnabled = false
        }
        else
        {
            login.isClickable = true
            login.isEnabled = true
        }
    }

    fun forgotPassword(view:View){
        startActivity(Intent(this, ForgotPassActivity::class.java))
    }
    fun register(view:View){
        startActivity(Intent(this, RegisterActivity::class.java))
    }
    fun logging(view:View){
        if(checkNetwork() == true ) {
            logUser()
            Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "You dont have connection right now", Toast.LENGTH_SHORT).show()
        }
    }
    private fun logUser(){

            val user: String = txtUser.text.toString()
            val password: String = txtPassword.text.toString()
            val keyStr: String = "Current_email_log"
            // save current user in case of lack of internet conection
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString(getString(R.string.Current_mail_user), user)
                commit()
            }
            //validate the user and the password and try to login

            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {
                progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(user, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progressBar.visibility = View.GONE
                        action()
                    } else {
                        Toast.makeText(this, "Error en la autenticacion", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }
                }
            }
//        else{
//            //and now we set sharedpreference then use this like
//            session.setusename("USERNAME");
//        }

    }
    private fun action(){
        startActivity(Intent(this, Home2::class.java))
    }


    fun loginFinger(view:View){
//        LoginFingerprint finger = new LoginFingerprint(this);
        startActivity(Intent(this, LoginFingerprint::class.java))
   }







}

