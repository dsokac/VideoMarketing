package hr.videomarketing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import hr.videomarketing.CustomViews.BottomBar.BottomBar;
import hr.videomarketing.CustomViews.BottomBar.Utils.BottomBarItem;
import hr.videomarketing.CustomViews.CustomBackButton;
import hr.videomarketing.Fragments.PlayVideoFragment;
import hr.videomarketing.Fragments.UserSettingsFragmet;
import hr.videomarketing.Fragments.UserStatusFragment;
import hr.videomarketing.Fragments.VideoListFragment;
import hr.videomarketing.Fragments.VideoListLikedFragment;
import hr.videomarketing.Fragments.VideoListSeenFragment;
import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.Utils.MyFiles;

import static hr.videomarketing.VideoMarketingApp.ACTIVE_FRAGMENT;
import static hr.videomarketing.VideoMarketingApp.PROVIDER;
import static hr.videomarketing.VideoMarketingApp.replaceFragment;
import static hr.videomarketing.VideoMarketingApp.requestPortraitOrientation;
import static hr.videomarketing.VideoMarketingApp.requestUnspecifiedOrientation;

public class HomeActivity extends AppCompatActivity implements  VideoListFragment.OnFragmentInteractionListener,
                                                                UserSettingsFragmet.OnFragmentInteractionListener,
                                                                View.OnClickListener,
                                                                UserStatusFragment.OnFragmentInteractionListener,
                                                                VideoListLikedFragment.OnFragmentInteractionListener,
                                                                VideoListSeenFragment.OnFragmentInteractionListener,
                                                                PlayVideoFragment.OnFragmentInteractionListener{





    private Toolbar toolbar=null;
    private int back = 0;
    private BottomBar bottomBar = null;
    private User activeUser = null;
    private CustomBackButton customBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);



        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        VideoMarketingApp.setOperator(manager.getNetworkOperator());
        VideoMarketingApp.setStatusBarColor(this);
        VideoMarketingApp.setNavigationBarColor(this);

        Object object = MyFiles.readFromFIle(this, MyFiles.Files.USER_DATA_FILE);
        if(object == null){
            goToLogIn(false);
        }
        else{
            activeUser = (User) object;
            if(!activeUser.checkIfLogedIn(this)){
                goToLogIn(true);
            }
            else {
                changeFragment(VideoListFragment.newInstance(activeUser.toString()));
            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        customBack = (CustomBackButton) findViewById(R.id.customBack);
        customBack.setOnClickListener(this);

        bottomBar.setOnItemsClickListener(this);
        bottomBar.setBackgroundColor(getResources().getColor(PROVIDER.getColors().getBottomBarColor()));
        toolbar.setBackgroundColor(getResources().getColor(PROVIDER.getColors().getToolbarColor()));
        customBack.setBackgroundColor(getResources().getColor(PROVIDER.getColors().getBottomBarColor()));

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_settings));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(back == 0){
                    changeFragment(UserSettingsFragmet.newInstance(activeUser.toString()));
                    hideBottomBar();
                    handleClick(3000);
                }
            }
        });
    }

    private void showBottomBar() {
        customBack.setVisibility(View.GONE);
        bottomBar.setVisibility(View.VISIBLE);
    }

    private void hideBottomBar() {
        bottomBar.setVisibility(View.GONE);
        customBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(activeUser!=null){
            outState.putString(getResources().getString(R.string.instance_state_user),activeUser.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(activeUser == null && savedInstanceState.isEmpty()){
            savedInstanceState.getString(getResources().getString(R.string.instance_state_user));
        }
    }

    @Override
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if(backStackCount>1){
            super.onBackPressed();
        }
        else {
            //TODO:implement exit
            if(back>=2){

                finish();
            }
            else{
                back++;
                Toast.makeText(this,getString(R.string.message_toast_press2ToExit),Toast.LENGTH_SHORT).show();
                handleClick(2000);
            }
        }
    }
    private void goToLogIn(boolean exists){
        Intent intent = new Intent(this,RegistrationActivity.class);
        intent.putExtra(getResources().getString(R.string.intent_logedIn),exists);
        if(activeUser !=null){
            intent.putExtra(getString(R.string.intent_extra_user),activeUser.toString());
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void updateUser() {

    }
    private void changeFragment(Fragment frag){
        replaceFragment(frag,R.id.fragmentContainer,getSupportFragmentManager());
    }

    @Override
    public void onClick(View view) {
        if(back == 0 ){
            if(view.getId() == toolbar.getId()){
                changeFragment(UserSettingsFragmet.newInstance(activeUser.toString()));
                requestPortraitOrientation(this);
            }
            else if (view instanceof BottomBarItem) {
                if (view.getId() == R.id.videoBBItem) {
                    changeFragment(VideoListFragment.newInstance(activeUser.toString()));
                    requestPortraitOrientation(this);
                    log("ItemClick>video");
                } else if (view.getId() == R.id.pointsBBItem) {
                    changeFragment(UserStatusFragment.newInstance(activeUser.toString()));
                    requestPortraitOrientation(this);
                    log("ItemClick>points");
                } else if (view.getId() == R.id.likesBBItem) {
                    changeFragment(VideoListLikedFragment.newInstance(activeUser.toString()));
                    requestPortraitOrientation(this);
                    log("ItemClick>likes");
                } else if (view.getId() == R.id.seenBBItem) {
                    changeFragment(VideoListSeenFragment.newInstance(activeUser.toString()));
                    requestPortraitOrientation(this);
                    log("ItemClick>seen");
                } else {
                    log("on click> view not found");
                }
            }
            handleClick(1000);
        }
        else if(view instanceof CustomBackButton){
            showBottomBar();
            onBackPressed();
        }
    }

    @Override
    public void onWebShopClick() {

    }

    @Override
    public void onUserSettingsClick() {

    }
    private void handleClick(int milisec){
        back++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                back = 0;
            }
        },milisec);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ACTIVE_FRAGMENT = null;
    }

    private void log(String text){
        VideoMarketingApp.log("Activity>Home>"+text);
    }

    @Override
    public void onLikedVideoClick(Video video) {
        startVideo(video);
    }

    @Override
    public void onSeenVideoClick(Video video) {
        startVideo(video);
    }

    @Override
    public void onVideoClick(Video video) {
        startVideo(video);
    }
    private void startVideo(Video vid){
        requestUnspecifiedOrientation(this);
        log("HomeActivity>SelectedVideo>"+vid.toJSON());
        PlayVideoFragment frag = PlayVideoFragment.newInstance(vid.toJSON());
        changeFragment(frag);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}