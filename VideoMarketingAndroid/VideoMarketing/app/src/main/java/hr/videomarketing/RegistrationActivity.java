package hr.videomarketing;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;

import hr.videomarketing.Fragments.LogInFragment;
import hr.videomarketing.Fragments.RegistrationFragment;
import hr.videomarketing.MyWebService.Interfaces.CheckPhonNuServiceInteraction;
import hr.videomarketing.MyWebService.Services.CheckPhoneNumberService;
import hr.videomarketing.Utils.MyFiles;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;
import static hr.videomarketing.VideoMarketingApp.replaceFragment;
import static hr.videomarketing.VideoMarketingApp.replaceFragmentWithoutBackStack;

public class RegistrationActivity extends AppCompatActivity
        implements RegistrationFragment.OnRegistrationFragmentInteraction,LogInFragment.OnFragmentInteractionListener {
    private final String ARG_PHONE_NUMBER="hr.videomarketing.user.phonenumber";
    private String phoneNumber=null;
    private boolean exist = false;
    private Toolbar toolbar = null;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        extras = getIntent().getExtras();
        if(extras != null && !extras.isEmpty()){
            exist = extras.getBoolean(getResources().getString(R.string.intent_logedIn),false);
        }
        if(exist){
            String user = extras.getString(getResources().getString(R.string.intent_extra_user));
            redirectToLogIn(user);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(phoneNumber != null && !phoneNumber.equals("")){
            outState.putString(ARG_PHONE_NUMBER,phoneNumber);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey(ARG_PHONE_NUMBER)){
            phoneNumber = savedInstanceState.getString(ARG_PHONE_NUMBER);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar = (Toolbar)findViewById(R.id.topNavBar);
        toolbar.setBackgroundColor(getResources().getColor(PROVIDER.getColors().getToolbarColor()));
        log("Debug");
        VideoMarketingApp.setStatusBarColor(this);
        VideoMarketingApp.setNavigationBarColor(this);
        log("Debug");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(phoneNumber == null){
            log("phoneNumber is null");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                requestPermission();
            }
            else{
                TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                log(manager.getLine1Number());
                setPhoneNumber(manager.getLine1Number());
                redirectToRegistration();
            }
        }
        else {
            redirectToRegistration();
        }
    }

    //user registration via service--async
    @Override
    public void redirectToLogIn(String user) {
        LogInFragment frag = LogInFragment.newInstance(user);
        replaceFragmentWithoutBackStack(frag,R.id.fragmentContainerRegistration,getSupportFragmentManager());
    }

    @Override
    public void redirectToRegistration() {
        RegistrationFragment frag = RegistrationFragment.newInstance(this.phoneNumber);
        replaceFragmentWithoutBackStack(frag,R.id.fragmentContainerRegistration,getSupportFragmentManager());
    }

    @Override
    public void userLogInSuccessful() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        log("OnPermissionResult");
        switch (requestCode){
            case VideoMarketingApp.PERMISSION_READ_PHONE_STATE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                    log(manager.getLine1Number());
                    setPhoneNumber(manager.getLine1Number());
                }
                else{
                    setPhoneNumber("");
                }
        }
    }

    private void setPhoneNumber(String line1Number) {
        if(line1Number != null){
            this.phoneNumber = line1Number;
        }
        //redirectToRegistration();
    }

    private void requestPermission(){
       if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            log("CheckPermission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, VideoMarketingApp.PERMISSION_READ_PHONE_STATE);
        }
        else {
           TelephonyManager manager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
           setPhoneNumber(manager.getLine1Number());
           redirectToRegistration();
        }
    }

    private void log(String text){
        VideoMarketingApp.log("RegistrationActivity>"+text);
    }
}

