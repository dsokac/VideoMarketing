package hr.videomarketing.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
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
import hr.videomarketing.Models.BaseModel.Video;
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
public class PlayVideoFragment extends Fragment implements DMWebVideoView.Listener, View.OnClickListener, View.OnTouchListener, PlayVideoFragmentDialog.PlayVideoFragmentDialogInterface {
    private static final String ARG_VIDEO = "hr.videomarketing.video";
    private Video video;
    private int likeStatus=0;
    private String comment = null;
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

    private OnFragmentInteractionListener mListener;

    public PlayVideoFragment() {
        // Required empty public constructor
    }
    public static PlayVideoFragment newInstance(String video) {
        PlayVideoFragment fragment = new PlayVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO, video);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.video = Video.newInstance(getArguments().getString(ARG_VIDEO));
        }

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
        commentAdd = false;
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
        log("onPause>CommentStatus>"+commentAdd);
        if(comment != null && commentAdd){
            log("onPause>Comment>"+comment);
            mListener.onUserComment(comment, video);
        }
        log("onPause>LikeStatus>"+likeStatus);
        log("onPause>VideoLikeStatus>"+video.getUserLike());
        if(likeStatus != video.getUserLike()){
            mListener.onUserLike(video);
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
                    mListener.onVideoEnded(video);
                    log("video ended>view count");
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
            dialog.show(getActivity().getSupportFragmentManager(),"PlayVideoFragmentDialog");
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
                }
                else{
                    Toast.makeText(getActivity(),"Morate prvo pogledati video da možete lajkati",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgbtnAddComment:
                if(dmWebVideoView.ended || video.getSeen()==1){
                    String comment = etComment.getText().toString();
                    if(comment != null && !comment.equals("") && !comment.equals(" ")){
                        commentAdd = true;
                        this.comment = comment;
                        etComment.setText("");
                        etComment.setHint(comment);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onVideoEnded(Video video);
        void onUserComment(String comment,Video video);
        void onUserLike(Video video);
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
    private void log(String text){
        VideoMarketingApp.log("Fragment>PlayVideo>"+text);
    }
}
