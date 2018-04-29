package com.moviles.kanoa.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.moviles.kanoa.R;

public class BLEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkBluetooth()== true ) {
            setContentView(R.layout.activity_ble);
        }
        else {
            Toast.makeText(this, "Oops! It seams you dont have your bluetooth on!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkBluetooth() {
        boolean hayE = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            hayE = true;
            Toast.makeText(this, "Maps is ready to be used!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Oops! It seems you dont have network connection :( maps feature may not work for you, so sorry!", Toast.LENGTH_SHORT).show();
            hayE = false;
        }
        return hayE;
    }

}
