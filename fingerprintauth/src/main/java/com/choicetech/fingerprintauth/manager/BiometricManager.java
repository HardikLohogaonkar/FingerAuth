package com.choicetech.fingerprintauth.manager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;

import com.choicetech.fingerprintauth.callback.BiometricCallbackV28;
import com.choicetech.fingerprintauth.callback.interfaces.BiometricCallback;
import com.choicetech.fingerprintauth.util.BiometricUtils;

public class BiometricManager extends BiometricManagerV23 {


    protected CancellationSignal mCancellationSignal = new CancellationSignal();

    protected BiometricManager(final BiometricBuilder biometricBuilder) {
        this.context = biometricBuilder.context;
        this.title = biometricBuilder.title;
        this.subtitle = biometricBuilder.subtitle;
        this.description = biometricBuilder.description;
        this.negativeButtonText = biometricBuilder.negativeButtonText;
        this.titlecolor=biometricBuilder.titlecolor;
        this.subtitlecolor=biometricBuilder.subtitlecolor;
        this.descriptioncolor=biometricBuilder.descriptioncolor;
        this.btncolor=biometricBuilder.btncolor;
        imglogo=biometricBuilder.drawable;
    }


    public void authenticate(@NonNull final BiometricCallback biometricCallback) {

        if(title == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog title cannot be null");
            return;
        }


        if(subtitle == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog subtitle cannot be null");
            return;
        }


        if(description == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog description cannot be null");
            return;
        }

        if(negativeButtonText == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog negative button text cannot be null");
            return;
        }


        if(!BiometricUtils.isSdkVersionSupported()) {
            biometricCallback.onSdkVersionNotSupported();
            return;
        }

        if(!BiometricUtils.isPermissionGranted(context)) {
            biometricCallback.onBiometricAuthenticationPermissionNotGranted();
            return;
        }

        if(!BiometricUtils.isHardwareSupported(context)) {
            biometricCallback.onBiometricAuthenticationNotSupported();
            return;
        }

        if(!BiometricUtils.isFingerprintAvailable(context)) {
            biometricCallback.onBiometricAuthenticationNotAvailable();
            return;
        }

        displayBiometricDialog(biometricCallback);
    }

    public void cancelAuthentication(){
        if(BiometricUtils.isBiometricPromptEnabled()) {
            if (!mCancellationSignal.isCanceled())
                mCancellationSignal.cancel();
        }else{
            if (!mCancellationSignalV23.isCanceled())
                mCancellationSignalV23.cancel();
        }
    }



    private void displayBiometricDialog(BiometricCallback biometricCallback) {
        if(BiometricUtils.isBiometricPromptEnabled()) {
            displayBiometricPrompt(biometricCallback);
        } else {
            displayBiometricPromptV23(biometricCallback);
        }
    }



    @TargetApi(Build.VERSION_CODES.P)
    private void displayBiometricPrompt(final BiometricCallback biometricCallback) {
        new BiometricPrompt.Builder(context)
                .setTitle(title)
                .setSubtitle(subtitle)
                .setDescription(description)

                .setNegativeButton(negativeButtonText, context.getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        biometricCallback.onAuthenticationCancelled();
                    }
                })
                .build()
                .authenticate(mCancellationSignal, context.getMainExecutor(),
                        new BiometricCallbackV28(biometricCallback));
    }


    public static class BiometricBuilder {

        private String title;
        private String subtitle;
        private String description;
        private String negativeButtonText;
        private Drawable drawable;
        private Context context;
        private int titlecolor,subtitlecolor,descriptioncolor,btncolor;
        public BiometricBuilder(Context context) {
            this.context = context;
        }

        public BiometricBuilder setTitle(@NonNull final String title) {
            this.title = title;
            return this;
        }

        public BiometricBuilder setSubtitle(@NonNull final String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public BiometricBuilder setDescription(@NonNull final String description) {
            this.description = description;
            return this;
        }

        public BiometricBuilder setTitleTextColor(@NonNull int titlecolor) {
            this.titlecolor = titlecolor;
            return this;
        }
        public BiometricBuilder setSubtitleTextColor(@NonNull int subtitlecolor) {
            this.subtitlecolor = subtitlecolor;
            return this;
        }
        public BiometricBuilder setItemDescriptionTextColor(@NonNull int descriptioncolor) {
            this.descriptioncolor = descriptioncolor;
            return this;
        }
        public BiometricBuilder setBtnCancelTextColor(@NonNull int btncolor) {
            this.btncolor = btncolor;
            return this;
        }


        public BiometricBuilder setNegativeButtonText(@NonNull final String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }
        public BiometricBuilder setImgLogo(@NonNull Drawable drawable){
            this.drawable=drawable;
            return this;
        }

        public BiometricManager build() {
            return new BiometricManager(this);
        }
    }
}
