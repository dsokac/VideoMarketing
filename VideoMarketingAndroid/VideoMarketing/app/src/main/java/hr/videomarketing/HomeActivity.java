package hr.videomarketing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hr.videomarketing.CustomViews.BottomBar.BottomBar;
import hr.videomarketing.CustomViews.BottomBar.Utils.BottomBarItem;
import hr.videomarketing.Fragments.PlayVideoFragment;
import hr.videomarketing.Fragments.UserSettingsFragmet;
import hr.videomarketing.Fragments.UserStatusFragment;
import hr.videomarketing.Fragments.VideoListFragment;
import hr.videomarketing.Fragments.VideoListLikedFragment;
import hr.videomarketing.Fragments.VideoListSeenFragment;
import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.MyWebService.Interfaces.OnUpdateUserProviderService;
import hr.videomarketing.MyWebService.Interfaces.VideoListInteractionService;
import hr.videomarketing.MyWebService.Services.AddUserAndProvider;
import hr.videomarketing.MyWebService.Services.ApplicationStateService;
import hr.videomarketing.MyWebService.Services.VideoListService;
import hr.videomarketing.Utils.Files;
import hr.videomarketing.Utils.VideoMarketingAppDialog;

import static hr.videomarketing.VideoMarketingApp.ACTIVE_FRAGMENT;
import static hr.videomarketing.VideoMarketingApp.HomeActivity;
import static hr.videomarketing.VideoMarketingApp.PROVIDER;
import static hr.videomarketing.VideoMarketingApp.replaceFragment;

public class HomeActivity extends AppCompatActivity implements  VideoListFragment.OnFragmentInteractionListener,
                                                                UserSettingsFragmet.OnFragmentInteractionListener,
                                                                View.OnClickListener,
                                                                UserStatusFragment.OnFragmentInteractionListener,
                                                                VideoListLikedFragment.OnFragmentInteractionListener,
                                                                VideoListSeenFragment.OnFragmentInteractionListener,
                                                                PlayVideoFragment.OnFragmentInteractionListener,
                                                                OnUpdateUserProviderService,
                                                                VideoListInteractionService,
                                                                ApplicationStateService.ApplicationStateListener{





    private Toolbar toolbar=null;
    private int back = 0;
    private BottomBar bottomBar = null;
    private User activeUser;
    private Video[] videos;
    private VideoListService videoService = null;
    private Video lastVideo;
    private boolean showMenuIconSave = false;
    private boolean refreshButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        new ApplicationStateService(this).execute();

        activeUser = new User();
        lastVideo = new Video();
        videos = new Video[0];
        if(activeUser == null){
            log("ActiveUser>null");
        }

        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        VideoMarketingApp.setOperator(manager.getNetworkOperator());
        VideoMarketingApp.setStatusBarColor(this);
        VideoMarketingApp.setNavigationBarColor(this);

        Object object = Files.USER_DATA_FILE.read(this);
        if(object == null){
            goToLogIn();
        }
        else{
            activeUser = (User) object;
            Object obj = Files.USER_PROVIDER.read(this);
            if(obj == null){
                new AddUserAndProvider(this,Long.toString(activeUser.getId()),PROVIDER.getCode()).execute();
                Files.USER_PROVIDER.write(this,"username="+activeUser.getUsername()+"&"+"provider="+PROVIDER.getCode());
            }
            changeFragment(VideoListFragment.newInstance(activeUser.toJSON()));
        }

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnItemsClickListener(this);
        bottomBar.setBackgroundColor(getResources().getColor(PROVIDER.getColors().getBottomBarColor()));
        toolbar.setBackgroundColor(getResources().getColor(PROVIDER.getColors().getToolbarColor()));


        Drawable icon = getResources().getDrawable(R.drawable.ic_settings);
        toolbar.setNavigationIcon(icon);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(back == 0){
                    changeFragment(UserSettingsFragmet.newInstance(activeUser.toJSON()));
                    handleClick(3000);
                }
            }
        });


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void setBottomBarVisibility(boolean visibility) {
        if(visibility){
            bottomBar.setVisibility(View.VISIBLE);
        }else {
            bottomBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSaveIconVisibility(boolean visibility) {
        showMenuIconSave = visibility;
        invalidateOptionsMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        VideoMarketingApp.HomeActivity = this;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(!activeUser.isNullObject()){
            outState.putString(getResources().getString(R.string.instance_state_user),activeUser.toJSON());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(activeUser.isNullObject() && savedInstanceState.isEmpty()){
            savedInstanceState.getString(getResources().getString(R.string.instance_state_user));
        }
    }

    @Override
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if(back>2){
            final VideoMarketingAppDialog dialog = new VideoMarketingAppDialog();
            dialog.setText(getResources().getString(R.string.alert_dialog_message_exit));
            dialog.setCheckBoxVisibility(View.GONE);
            dialog.setTitle(getResources().getString(R.string.action_exit));
            dialog.setPostivieButtonText(getResources().getString(R.string.action_exit));
            dialog.setNegativeButtonText(getString(R.string.alert_dialog_message_negative));
            dialog.setPositiveButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.setNegativeButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(dialog.isVisible()){
                        dialog.dismiss();
                    }
                }
            });
            dialog.show(getSupportFragmentManager(),"VideoMarketingAppDialogOnExit");
        }
        else {
            back++;
            Toast.makeText(this,"Za izlaz pritisnite jos "+(3-back)+" puta brzo",Toast.LENGTH_SHORT).show();
            handleClick(2000);
            if(backStackCount>1){
                super.onBackPressed();
            }
        }
    }
    private void goToLogIn(){
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    private void changeFragment(Fragment frag){
        replaceFragment(frag,R.id.fragmentContainer,getSupportFragmentManager());
    }

    @Override
    public void onClick(View view) {
        if(back == 0 ){
            if(view.getId() == toolbar.getId()){
                changeFragment(UserSettingsFragmet.newInstance(activeUser.toJSON()));
            }
            else if (view instanceof BottomBarItem) {
                if (view.getId() == R.id.videoBBItem) {
                    changeFragment(VideoListFragment.newInstance(activeUser.toJSON()));
                    log("ItemClick>video");
                } else if (view.getId() == R.id.pointsBBItem) {
                    changeFragment(UserStatusFragment.newInstance(activeUser.toJSON()));
                    log("ItemClick>points");
                } else if (view.getId() == R.id.likesBBItem) {
                    changeFragment(VideoListLikedFragment.newInstance(activeUser.toJSON()));
                    log("ItemClick>likes");
                } else if (view.getId() == R.id.seenBBItem) {
                    changeFragment(VideoListSeenFragment.newInstance(activeUser.toJSON()));
                    log("ItemClick>seen");
                } else {
                    log("on click> view not found");
                }
            }
            handleClick(1000);
        }
    }

    @Override
    public void onWebShopClick() {
        //TODO: Open webpage of operator
        Intent goTOPage = new Intent(Intent.ACTION_VIEW,Uri.parse(PROVIDER.getLink()));
        startActivity(goTOPage);
    }

    @Override
    public void onUserSettingsClick() {
        UserSettingsFragmet frag = UserSettingsFragmet.newInstance(activeUser.toJSON());
        changeFragment(frag);
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
        VideoMarketingApp.HomeActivity = null;
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
        Intent i = new Intent(HomeActivity.this,PlayVideoActivity.class);
        i.putExtra(getResources().getString(R.string.intent_params_user),activeUser.toJSON());
        i.putExtra(getResources().getString(R.string.intent_params_video),vid.toJSON());
        startActivity(i);
    }

    @Override
    public void updateVideo(Video video) {
        int index = findVideoInList(video);
        if(index != -1){
            videos[index] = video;
        }
    }
    @Override
    public Video[] getVideos(String condition) {
        switch (condition){
            case "seen":
                return seenVideos();
            case "liked":
                return likedVideo();
            default:
                return this.videos != null && this.videos.length>0?this.videos:null;
        }

    }

    @Override
    public void updateList(boolean showProgressBar) {
        videoService = new VideoListService(activeUser.getId(),this);
        videoService.setProgressDialog(getResources().getString(R.string.message_get_videos));
        videoService.execute();
    }

    @Override
    public void saveVideoList(Video[] videoList) {
        this.videos = videoList;
    }

    private Video[] seenVideos(){
        List<Video> seen = new ArrayList<>();
        for (Video vid:this.videos) {
            if(vid.getSeen() == 1){
                seen.add(vid);
            }
        }
        return seen.toArray(new Video[seen.size()]);
    }
    private Video[] likedVideo(){
        List<Video> like = new ArrayList<>();
        for (Video vid:this.videos) {
            if(vid.getUserLike() == 1){
                like.add(vid);
            }
        }
        return like.toArray(new Video[like.size()]);
    }
    private int findVideoInList(Video vid){
        log("Searching for: "+vid.getId());
        for (int i = 0; i < videos.length; i++) {
            if(vid.getId()==videos[i].getId()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onUserProviderUpdated(boolean success) {
        if(success){
            log("AddingOperatorToUser>successful");
        }
        else{
            log("AddingOperatorToUser>unsuccessful");
        }
    }

    @Override
    public void onVideosReady(Video[] videoList) {
        if(videoList.length > 0){
            this.videos = videoList;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        MenuItem item = menu.findItem(R.id.action_save);
        Drawable iconsave = getResources().getDrawable(R.drawable.ic_save);
        iconsave.setColorFilter(getResources().getColor(PROVIDER.getColors().getLinesColor()), PorterDuff.Mode.SRC_ATOP);
        item.setIcon(iconsave);
        MenuItem refreshItem = menu.findItem(R.id.action_refresh_video);
        Drawable iconRefresh = getResources().getDrawable(R.drawable.ic_refresh);
        iconRefresh.setColorFilter(getResources().getColor(PROVIDER.getColors().getLinesColor()), PorterDuff.Mode.SRC_ATOP);
        item.setVisible(showMenuIconSave);
        refreshItem.setIcon(iconRefresh);
        refreshItem.setVisible(refreshButton);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void userUpdated(User user) {
        this.activeUser = user;
    }

    @Override
    public void setRefreshButtonVisibility(boolean visibility) {
        this.refreshButton = visibility;
        invalidateOptionsMenu();
    }

    @Override
    public void onApplicationStateReceived(int state) {
        log("State "+state);
        if(state == 1){
            VideoMarketingAppDialog prompt = new VideoMarketingAppDialog();
            prompt.setText("Održavanje u tijeku");
            prompt.setTitle("Obavijest");
            prompt.setPostivieButtonText("Izlaz");
            prompt.setPositiveButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    HomeActivity.this.finish();
                }
            });
            prompt.show(getSupportFragmentManager(),"MaintenanceDialog");
        }
    }
}