package hr.videomarketing.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import hr.videomarketing.DMVideoView.DMWebVideoView;
import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.MyWebService.Interfaces.OnVideoInteractionService;
import hr.videomarketing.MyWebService.Services.CommentVideoService;
import hr.videomarketing.MyWebService.Services.LikeVideoService;
import hr.videomarketing.MyWebService.Services.VideoSeenService;
import hr.videomarketing.MyWebService.VideoActions;
import hr.videomarketing.R;
import hr.videomarketing.Utils.DialogStateAdapter;
import hr.videomarketing.Utils.PlayVideoFragmentDialog;
import hr.videomarketing.Utils.VideoMarketingAppDialog;
import hr.videomarketing.VideoMarketingApp;


import static hr.videomarketing.VideoMarketingApp.PROVIDER;
import static hr.videomarketing.VideoMarketingApp.hideSoftKeyboard;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayVideoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayVideoFragment extends Fragment implements DMWebVideoView.Listener, View.OnClickListener, View.OnTouchListener, PlayVideoFragmentDialog.PlayVideoFragmentDialogInterface, View.OnFocusChangeListener, OnVideoInteractionService {
    private static final String ARG_VIDEO = "hr.videomarketing.playfragment.video";
    private static final String ARG_USER = "hr.videomarketing.playfragment.user";
    private Video video;
    private User user;
    private int likeStatus=0;
    private String comment = "";
    private boolean commentAdd;
    private EditText etComment;
    private ImageButton imgbtnLike;
    private ImageButton imgbtnDislike;
    private ImageView iwBack;
    private DMWebVideoView dmWebVideoView;
    private TextView twVideoName;
    private ImageButton imgbtnAddComment;
    private ProgressDialog progressDialog;
    private double watchedTime;
    private ImageButton btnReturnToWatched;
    private RelativeLayout seekContainer;
    private RelativeLayout likeContainer;
    PlayVideoFragmentDialog dialog;
    private boolean seeked = false;

    int likeSpam=0;
    int commentSpamer=0;

    private OnFragmentInteractionListener mListener;

    public PlayVideoFragment() {
        // Required empty public constructor
    }
    public static PlayVideoFragment newInstance(String video, String user) {
        PlayVideoFragment fragment = new PlayVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO, video);
        args.putString(ARG_USER,user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.video = Video.newInstance(getArguments().getString(ARG_VIDEO));
            this.user = User.newInstance(getArguments().getString(ARG_USER));
        }
        else{
            this.video = new Video();
            this.user = new User();
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_video, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dmWebVideoView = (DMWebVideoView) view.findViewById(R.id.dmVideoViewMain);
        dmWebVideoView.setEventListener(this);
        etComment = (EditText)view.findViewById(R.id.etComment);
        etComment.setOnFocusChangeListener(this);
        imgbtnLike = (ImageButton)view.findViewById(R.id.imbtnLike);
        imgbtnLike.setOnClickListener(this);
        imgbtnDislike = (ImageButton)view.findViewById(R.id.imbtnDislike);
        imgbtnDislike.setOnClickListener(this);
        iwBack  = (ImageView)view.findViewById(R.id.iwIconBack);
        iwBack.setOnClickListener(this);
        twVideoName = (TextView)view.findViewById(R.id.lblVideoName);
        imgbtnAddComment = (ImageButton)view.findViewById(R.id.imgbtnAddComment);
        imgbtnAddComment.setOnClickListener(this);
        btnReturnToWatched = (ImageButton)view.findViewById(R.id.btnReturnToWatched);
        btnReturnToWatched.setOnClickListener(this);
        seekContainer = (RelativeLayout)view.findViewById(R.id.seekingContainer);
        likeContainer = (RelativeLayout)view.findViewById(R.id.likeContainer);
        if(!video.isNullObject()){
            twVideoName.setText(video.getTitle());
            progressDialog = new ProgressDialog(getActivity());
            if(video.getSeen() == 1){
                likeContainer.setVisibility(View.VISIBLE);
                likeStatus = video.getUserLike();
                setVideoLike(video.getUserLike());
            }
            likeStatus = video.getUserLike();
            dmWebVideoView.setVideoId(video.getDmID());
            dmWebVideoView.setControls(false);
            dmWebVideoView.setAutoPlay(false);
            dmWebVideoView.load();
            imgbtnLike.setOnClickListener(this);
            progressDialog.setMessage(getResources().getString(R.string.message_video_loading));
            progressDialog.show();
        }
        view.setOnTouchListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        likeSpam=0;
        commentSpamer=0;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        dmWebVideoView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){

        }
    }

    @Override
    public void onEvent(String event) {
        switch (event){
            case "apiready":
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                showWarning();
                log("dailymotion>videoview>event>apiready");
                break;
            case "start":
                watchedTime = dmWebVideoView.currentTime;
                seeked = false;
                seekContainer.setVisibility(View.INVISIBLE);
                log("dailymotion>videoview>event>start");
                break;
            case "end":
                if(!seeked && video.getSeen() == 0){
                    likeContainer.setVisibility(View.VISIBLE);
                    setVideoLike(0);
                    video.setSeen(1);
                    addVideoView();
                    Toast.makeText(getActivity(),getResources().getString(R.string.toast_message_view_count),Toast.LENGTH_SHORT).show();
                    mListener.updateVideo(video);
                }
                seekContainer.setVisibility(View.INVISIBLE);
                log("dailymotion>videoview>event>end");
                break;
            case "video_end":
                log("dailymotion>videoview>event>video_end");
                break;
            case "seeking":
                if(watchedTime<dmWebVideoView.currentTime && !seeked){
                    watchedTime = dmWebVideoView.currentTime;
                    seeked = true;
                    log("dailymotion>videoview>event>seeking>time watched>"+watchedTime);
                }
                log("dailymotion>videoview>event>seeking>seekd="+seeked);
                break;
            case "play":
                log("dailymotion>videoview>event>play>timeupdate>current time"+dmWebVideoView.currentTime);
                log("dailymotion>videoview>event>play>>timeupdate>watched"+watchedTime);
                break;
            case "seeked":
                if(watchedTime>dmWebVideoView.currentTime){
                    seeked = false;
                    seekContainer.setVisibility(View.INVISIBLE);
                }else{
                    seekContainer.setVisibility(View.VISIBLE);
                }
                log("dailymotion>videoview>event>seeked="+seeked);
                break;
            case "video_start":
                log("dailymotion>videoview>event>video_start");
                break;
            case "timeupdate":
                //log("dailymotion>videoview>event>timeupdate>"+dmWebVideoView.currentTime);
                //log("dailymotion>videoview>event>timeupdate>"+watchedTime);
                break;
            case "rebuffer":
                log("event>rebuffer");
                break;
            case "loadedmetadata":
                log("event>loadedmetadata");
                break;
            case "progress":
                log("event>progress");
                break;
            case "durationchanged":
                log("event>durationchanged");
                break;
            case "fullscreenchange":
                if(dmWebVideoView.isFullscreen()){
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else{
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
        }
    }

    private void showWarning() {
        if(DialogStateAdapter.isShowing(getActivity()) && video.getSeen() == 0){
            final VideoMarketingAppDialog dialog = new  VideoMarketingAppDialog();
            dialog.setText(getResources().getString(R.string.message_my_dialog_content));
            dialog.setPostivieButtonText("OK");
            dialog.setTitle(getResources().getString(R.string.message_warning_title));
            dialog.setCheckBoxVisibility(View.VISIBLE);
            dialog.setPositiveButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(dialog.isCheckBoxVisible()){
                        if(dialog.getCheckedStatus()){
                            //Not to show in future...
                            DialogStateAdapter.saveStatus(getActivity(),false);
                        }else{
                            //Show in future...
                            DialogStateAdapter.saveStatus(getActivity(),true);
                        }
                    }
                }
            });
            log("crash time>");
            if(isResumed())dialog.show(getActivity().getSupportFragmentManager(),"PlayVideoFragmentDialog");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iwIconBack:
                dmWebVideoView.pause();
                getActivity().onBackPressed();
                break;
            case R.id.imbtnLike:
                if(video.getSeen() == 1){
                    if(video.getUserLike() == 1){
                        video.setUserLike(0);
                        setVideoLike(0);
                    }
                    else {
                        video.setUserLike(1);
                        setVideoLike(1);
                    }
                    if(!addLike()){
                        Toast.makeText(getActivity(),"Stop spam!",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Morate prvo pogledati video da možete lajkati",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imbtnDislike:
                if(video.getSeen() == 1){
                    if(video.getUserLike() == -1){
                        video.setUserLike(0);
                        setVideoLike(0);
                    }
                    else {
                        video.setUserLike(-1);
                        setVideoLike(-1);
                    }
                    if(!addLike()){
                        Toast.makeText(getActivity(),"Stop spam!",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Morate prvo pogledati video da možete lajkati",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgbtnAddComment:
                if(dmWebVideoView.ended || video.getSeen()==1){
                    String comment = etComment.getText().toString();
                    if(!comment.equals("") && comment.length()>1){
                        Toast.makeText(getActivity(),"Komentar: "+comment+" dodan",Toast.LENGTH_SHORT).show();
                        if(addVideoComment(comment)){
                            etComment.setText("");
                        }
                    }
                    else{
                        Toast.makeText(getActivity(),"Neispravan komentar",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Morate prvo pogledati video da možete lajkati",Toast.LENGTH_SHORT).show();
                    etComment.setText("");
                }
                break;
            case R.id.btnReturnToWatched:
                dmWebVideoView.seek(watchedTime-0.05);
                seekContainer.setVisibility(View.INVISIBLE);
                break;

        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        hideSoftKeyboard(getActivity());
        return false;
    }

    @Override
    public void onOKClick() {
        if(dialog != null && dialog.isVisible())dialog.dismiss();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(view.getId() == etComment.getId()){
            if(b){
                etComment.setText("");
            }
        }
    }

    @Override
    public void onVideoInteractionService(VideoActions action, int succes, String message) {
        if(succes == 1){
            switch (action){
                case SEEN:
                    log("VideoService>Seen>success");
                    break;
                case LIKE:
                    log("VideoService>Like>success");
                    break;
                case COMMENT:
                    log("VideoService>Comment>success");
                    break;
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void updateVideo(Video video);
    }
    private void setVideoLike(int like){
        switch (like){
            case -1:
                imgbtnDislike.setColorFilter(getResources().getColor(PROVIDER.getColors().getLinesColor()));
                imgbtnLike.setColorFilter(Color.WHITE);
                break;
            case 1:
                imgbtnLike.setColorFilter(getResources().getColor(PROVIDER.getColors().getLinesColor()));
                imgbtnDislike.setColorFilter(Color.WHITE);
                break;
            default:
                imgbtnDislike.setColorFilter(Color.WHITE);
                imgbtnLike.setColorFilter(Color.WHITE);
        }
    }
    private void addVideoView(){
        new VideoSeenService(this,Long.toString(user.getId()),Integer.toString(video.getId())).execute();
    }
    private boolean addVideoComment(String comment){
        if(commentSpamer == 0){
            handleSpam("comment",10000);
        }
        if(commentSpamer<3){
            new CommentVideoService(this,Integer.toString(video.getId()),Long.toString(user.getId()),comment).execute();
            commentSpamer++;
            return true;
        }
        return false;
    }
    private boolean addLike(){
        if(likeSpam == 0){
            handleSpam("like",10000);
        }
        if(likeSpam<4){
            new LikeVideoService(this,Long.toString(user.getId()),Integer.toString(video.getId()),Integer.toString(video.getUserLike())).execute();
            likeSpam++;
            return true;
        }
        return false;
    }
    private void log(String text){
        VideoMarketingApp.log("Fragment>PlayVideo>"+text);
    }
    private void handleSpam(final String action, int duration){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (action){
                    case "like":
                        likeSpam = 0;
                        break;
                    case "comment":
                        commentSpamer = 0;
                        break;
                }
            }
        },duration);
    }
}
