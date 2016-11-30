package hr.videomarketing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import hr.videomarketing.Fragments.LogInFragment;
import hr.videomarketing.Fragments.RegistrationFragment;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;
import static hr.videomarketing.VideoMarketingApp.replaceFragment;

public class RegistrationActivity extends AppCompatActivity
        implements RegistrationFragment.OnRegistrationFragmentInteraction,LogInFragment.OnFragmentInteractionListener{

    private boolean exist = false;
    private Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Bundle extras = getIntent().getExtras();
        if(extras != null && !extras.isEmpty()){
            //If user exist
            exist = extras.getBoolean(getResources().getString(R.string.intent_logedIn),false);
        }
        if(exist){
            String user = extras.getString(getResources().getString(R.string.intent_extra_user));
            LogInFragment frag = LogInFragment.newInstance(user);
            replaceFragment(frag,R.id.fragmentContainerRegistration,getSupportFragmentManager());
        }
        else{
            replaceFragment(new RegistrationFragment(),R.id.fragmentContainerRegistration,getSupportFragmentManager());
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
        VideoMarketingApp.setStatusBarColor(this);
        VideoMarketingApp.setNavigationBarColor(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //user registration via service--async
    @Override
    public void redirectToLogIn(String user) {
        LogInFragment frag = LogInFragment.newInstance(user);
        replaceFragment(frag,R.id.fragmentContainerRegistration,getSupportFragmentManager());
    }

    @Override
    public void redirectToRegistration() {
        replaceFragment(new RegistrationFragment(),R.id.fragmentContainerRegistration,getSupportFragmentManager());
    }

    @Override
    public void userLogInSuccessful() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

}

