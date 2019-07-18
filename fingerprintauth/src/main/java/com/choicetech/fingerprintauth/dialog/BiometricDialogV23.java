package com.choicetech.fingerprintauth.dialog;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.choicetech.fingerprintauth.R;
import com.choicetech.fingerprintauth.callback.interfaces.BiometricCallback;


import static android.content.Context.KEYGUARD_SERVICE;


public class BiometricDialogV23 extends BottomSheetDialog implements View.OnClickListener, DialogInterface {

    private Context context;
    private static final int INTENT_AUTHENTICATE = 1;
    private Button btnCancel;
    private ImageView imgLogo;
    private TextView itemTitle, itemDescription, itemSubtitle, itemStatus;

    private BiometricCallback biometricCallback;


    public BiometricDialogV23(@NonNull Context context) {
        super(context, R.style.BottomSheetDialogTheme);
        this.context = context;
        setDialogView();
    }

    public BiometricDialogV23(@NonNull Context context, BiometricCallback biometricCallback) {
        super(context, R.style.BottomSheetDialogTheme);
        this.context = context;
        this.biometricCallback = biometricCallback;
        setDialogView();
    }

    public BiometricDialogV23(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BiometricDialogV23(@NonNull Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;

        setDialogView();
    }

    private void setDialogView() {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.view_bottom_sheet, null);
        setContentView(bottomSheetView);

        btnCancel = findViewById(R.id.btn_cancel);

        imgLogo = findViewById(R.id.img_logo);
        itemTitle = findViewById(R.id.item_title);
        itemStatus = findViewById(R.id.item_status);
        itemSubtitle = findViewById(R.id.item_subtitle);
        itemDescription = findViewById(R.id.item_description);

        btnCancel.setOnClickListener(this);

        updateLogo();
    }

    public void setTitle(String title) {
        itemTitle.setText(title);
    }

    public void updateStatus(String status) {
        itemStatus.setText(status);
    }

    public void setSubtitle(String subtitle) {
        itemSubtitle.setText(subtitle);
    }

    public void setDescription(String description) {
        itemDescription.setText(description);
    }

    public void setButtonText(String negativeButtonText) {
        btnCancel.setText(negativeButtonText);
    }

    public void setImgLogo(Drawable drawable){
        imgLogo.setImageDrawable(drawable);
    }

    public void setTitleTextColor(@NonNull int titlecolor){
        itemTitle.setTextColor(titlecolor);
    }
    public void setSubtitleTextColor(@NonNull int colors){
        itemSubtitle.setTextColor(colors);

    }
    public void setItemDescriptionTextColor(@NonNull int colors){
        itemDescription.setTextColor(colors);

    }
    public void setBtnCancelTextColor(@NonNull int colors){
        btnCancel.setTextColor(colors);

    }

    private void updateLogo() {
        try {
            Drawable drawable = getContext().getPackageManager().getApplicationIcon(context.getPackageName());
            imgLogo.setImageDrawable(drawable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == btnCancel.getId()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                KeyguardManager km = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);

                if (km.isKeyguardSecure()) {
                    Intent authIntent = km.createConfirmDeviceCredentialIntent(context.getString(R.string.dialog_title_auth), context.getString(R.string.dialog_msg_auth));
                    ((Activity) context).startActivityForResult(authIntent, INTENT_AUTHENTICATE);
                }
            }
            dismiss();
            biometricCallback.onAuthenticationCancelled();
        }
    }
}