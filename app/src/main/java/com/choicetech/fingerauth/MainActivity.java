package com.choicetech.fingerauth;



import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.choicetech.fingerprintauth.callback.interfaces.BiometricCallback;
import com.choicetech.fingerprintauth.manager.BiometricManager;

public class MainActivity extends AppCompatActivity implements BiometricCallback {

    private static final int INTENT_AUTHENTICATE = 1;
    BiometricManager mBiometricManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onAuth();
    }

    @Override
    public void onSdkVersionNotSupported() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (km.isKeyguardSecure()) {
                Intent authIntent = km.createConfirmDeviceCredentialIntent(getString(R.string.dialog_title_auth), getString(R.string.dialog_msg_auth));
                startActivityForResult(authIntent, INTENT_AUTHENTICATE);
            }
        }
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (km.isKeyguardSecure()) {
                Intent authIntent = km.createConfirmDeviceCredentialIntent(getString(R.string.dialog_title_auth), getString(R.string.dialog_msg_auth));
                startActivityForResult(authIntent, INTENT_AUTHENTICATE);
            }
        }
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (km.isKeyguardSecure()) {
                Intent authIntent = km.createConfirmDeviceCredentialIntent(getString(R.string.dialog_title_auth), getString(R.string.dialog_msg_auth));
                startActivityForResult(authIntent, INTENT_AUTHENTICATE);
            }
        }

    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (km.isKeyguardSecure()) {
                Intent authIntent = km.createConfirmDeviceCredentialIntent(getString(R.string.dialog_title_auth), getString(R.string.dialog_msg_auth));
                startActivityForResult(authIntent, INTENT_AUTHENTICATE);
            }
        }
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (km.isKeyguardSecure()) {
                Intent authIntent = km.createConfirmDeviceCredentialIntent(getString(R.string.dialog_title_auth), getString(R.string.dialog_msg_auth));
                startActivityForResult(authIntent, INTENT_AUTHENTICATE);
            }
        }

    }

    @Override
    public void onAuthenticationFailed() {

    }

    @Override
    public void onAuthenticationCancelled() {
        mBiometricManager.cancelAuthentication();
    }

    @Override
    public void onAuthenticationSuccessful() {

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT_PERMANENT) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                if (km.isKeyguardSecure()) {
                    Intent authIntent = km.createConfirmDeviceCredentialIntent(getString(R.string.dialog_title_auth), getString(R.string.dialog_msg_auth));
                    startActivityForResult(authIntent, INTENT_AUTHENTICATE);
                }

                Log.d("errorLockoutPermanent", "" + errorCode);
            }

        } else if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                if (km.isKeyguardSecure()) {
                    Intent authIntent = km.createConfirmDeviceCredentialIntent(getString(R.string.dialog_title_auth), getString(R.string.dialog_msg_auth));
                    startActivityForResult(authIntent, INTENT_AUTHENTICATE);
                    Log.d("errorLockout", "" + errorCode);
                }
            }
        } else if (errorCode == FingerprintManager.FINGERPRINT_ERROR_USER_CANCELED) {
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_AUTHENTICATE) {
            if (resultCode == RESULT_OK) {
                //do something you want when pass the security

//                onAuth();
            }else {
                finish();
            }
        }
    }

    private void onAuth() {

        mBiometricManager = new BiometricManager.BiometricBuilder(this)
                .setTitle(getString(R.string.biometric_title))
                .setSubtitle(getString(R.string.biometric_subtitle))
                .setDescription(getString(R.string.biometric_description))
                .setImgLogo(getResources().getDrawable(R.drawable.ic_android_black_24dp))
                .setTitleTextColor(getResources().getColor(R.color.errorText))
                .setSubtitleTextColor(getResources().getColor(R.color.errorText))
                .setItemDescriptionTextColor(getResources().getColor(R.color.errorText))
                .setBtnCancelTextColor(getResources().getColor(R.color.errorText))
                .setNegativeButtonText(getString(R.string.use_scree))
                .build();

        mBiometricManager.authenticate(this);
    }
}
