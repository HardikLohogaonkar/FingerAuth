# FingerAuth
Fingerprint library


How to use

Add the following library in your build.gradle(Project) file

 dependencies {
	        implementation 'com.github.HardikLohogaonkar:FingerAuth:v1.0'
	}
	
	
Add the following JitPack repository in your build.gradle(Project) 	

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


implement BiometricCallback to your respective Activity/Fragment 


Add  the variable BiometricManager to your Activity/Fragment as follows

private static final int INTENT_AUTHENTICATE = 1;                        
BiometricManager mBiometricManager;


Customize your Fingerprint Dialog as following

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
 
 
 
 Override the onActivityResult method to your Activity/Fragment. This is use if user cancels the operation or user wants to use their screen lock/Pattern/Password.
 
 @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_AUTHENTICATE) {
            if (resultCode == RESULT_OK) {

                
            }else {
                finish();
            }
        }
    }


 
