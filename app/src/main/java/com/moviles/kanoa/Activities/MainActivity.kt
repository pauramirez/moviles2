package com.moviles.kanoa.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.moviles.kanoa.R

import com.newrelic.agent.android.NewRelic;

class MainActivity : AppCompatActivity() {

    val SPLASH_TIME_OUT = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed(object : Runnable{
            override fun run() {
                val home = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(home)
                finish()
            }
        }, SPLASH_TIME_OUT.toLong())

        NewRelic.withApplicationToken(
                "AA50da8e2ec331f9c25f70e1aad0f0cc9e2fd050d8"
        ).start(this.getApplication());

    }
}
