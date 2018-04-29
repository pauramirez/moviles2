package com.moviles.kanoa.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context cxt) {
        this.context = cxt;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startAuth(FingerprintManager fingerManager, FingerprintManager.CryptoObject cryptFinger) {

        CancellationSignal cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
            return;
        fingerManager.authenticate(cryptFinger,cancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationFailed(){
        super.onAuthenticationFailed();
        Toast.makeText(context, "Fingerprint auth failed!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result){
        super.onAuthenticationSucceeded(result);
        context.startActivity(new Intent(context,MainActivity.class));
    }

}
