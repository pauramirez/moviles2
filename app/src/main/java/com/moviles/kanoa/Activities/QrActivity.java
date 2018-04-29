package com.moviles.kanoa.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class QrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(QrActivity.this, "Permission is Granted!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }

    public boolean checkNetwork() {
        boolean hayE = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            hayE = true;
            Toast.makeText(this, "QR scanner is ready to be used", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Oops! It seems you dont have network connection :( QR scan may not work for you, so sorry!", Toast.LENGTH_SHORT).show();
            hayE = false;
        }
        return hayE;
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(QrActivity.this, CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[] {CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionResult(int requestCode, String permission[], int grantResults[])
    {
        if (checkNetwork() == true) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    if (grantResults.length > 0) {
                        boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        if (cameraAccepted) {
                            Toast.makeText(QrActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(QrActivity.this, "Permission Denided", Toast.LENGTH_LONG).show();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (shouldShowRequestPermissionRationale(CAMERA)) {
                                    displayAlertMessage("Allow Access",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                    }
                                                }
                                            });
                                    return;
                                }
                            }
                        }
                    }
                    break;
            }
        }
        else{
            Toast.makeText(this, "Oops! It seems you dont have network connection :( QR scan may not work for you, so sorry!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                if(scannerView==null)
                {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else
            {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String msg, DialogInterface.OnClickListener listener)
    {
        new AlertDialog.Builder(QrActivity.this)
                .setMessage(msg)
                .setPositiveButton("Ok", listener)
                .setNeutralButton("Cancel",null)
                .create()
                .show();
    }

    @Override
    public void handleResult(final Result result) {
        final String myResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scann Result");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                scannerView.resumeCameraPreview(QrActivity.this);
            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myResult));
                startActivity(intent);
            }
        });
        builder.setMessage(myResult);
        AlertDialog alert = builder.create();
        alert.show();

    }
}
