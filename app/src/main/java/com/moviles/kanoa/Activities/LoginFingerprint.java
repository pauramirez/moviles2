package com.moviles.kanoa.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import com.moviles.kanoa.R;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginFingerprint extends AppCompatActivity{

    private KeyStore keyStore;
    private static final String KEY_NAME="LOGIN";
    private Cipher cipher;
    private TextView textView;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        KeyguardManager keyMan = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerManager = (FingerprintManager)getSystemService(FINGERPRINT_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED){
            return;
        }

        if(!fingerManager.isHardwareDetected())
            Toast.makeText(this, "Fingerprint auth failed",Toast.LENGTH_SHORT).show();
        else{
            if(!fingerManager.hasEnrolledFingerprints())
                Toast.makeText(this,"Register at least one fingerprint in your phone settings",Toast.LENGTH_SHORT).show();
            else{
                if(!keyMan.isKeyguardSecure())
                    Toast.makeText(this, "Lock screen not enabled in settings ", Toast.LENGTH_SHORT).show();
                else 
                    genKey();

                if(cipherInit()){
                    FingerprintManager.CryptoObject cryptFinger = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuth(fingerManager, cryptFinger);
                }
            }
        }


    }

    private boolean cipherInit() {

        boolean res = false;

        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            try {
                keyStore.load(null);
                SecretKey key =(SecretKey)keyStore.getKey(KEY_NAME,null);
                cipher.init(Cipher.ENCRYPT_MODE,key);
                res = true;
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            } catch (CertificateException e1) {
                e1.printStackTrace();
            } catch (UnrecoverableKeyException e1) {
                e1.printStackTrace();
            } catch (KeyStoreException e1) {
                e1.printStackTrace();
            } catch (InvalidKeyException e1) {
                e1.printStackTrace();
            }
        }
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void genKey() {

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = null;

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build()
            );
            keyGenerator.generateKey();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }


    }


}
