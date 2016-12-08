package hr.videomarketing.Fragments;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.Models.VideoAdapter;
import hr.videomarketing.MyWebService.Interfaces.OnVideoThumbnailDownloaded;
import hr.videomarketing.MyWebService.Interfaces.VideoListInteractionService;
import hr.videomarketing.MyWebService.Services.VideoListService;
import hr.videomarketing.R;
import hr.videomarketing.Utils.VideoClickListener;
import hr.videomarketing.Utils.VideoTransfer;
import hr.videomarketing.VideoMarketingApp;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoListFragment extends Fragment implements VideoClickListener,VideoListInteractionService, View.OnClickListener, OnVideoThumbnailDownloaded {

    private static final String ARG_USER = "hr.videomarketing.user_extras";
    private User user;
    private TextView twTop;
    private OnFragmentInteractionListener mListener;
    private ImageButton imBtnVVTop;
    private GridView gridView = null;
    private Video[] videos=null;
    private int topVideo;

    public VideoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment VideoListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoListFragment newInstance(String param1) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            user = User.newInstance(getArguments().getString(ARG_USER));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_USER,user.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if(user == null){
            if(savedInstanceState != null && !savedInstanceState.isEmpty()){
                user = User.newInstance(savedInstanceState.getString(ARG_USER));
            }
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_list_layout, container, false);

    }

    @Override
    public void onStart() {
        if(videos == null || videos.length ==0){
            Video[] vid = mListener.getVideos("");
            if(vid == null){
                getVideos();
            }
        }
        else{
            setVideos();
        }
        super.onStart();
    }

    private void getVideos() {
        VideoListService videoListService = new VideoListService(user.getId(),this);
        videoListService.setProgressDialog(getResources().getString(R.string.message_get_videos));
        videoListService.execute();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = (GridView) view.findViewById(R.id.gvVideoList);
        twTop = (TextView) view.findViewById(R.id.twLabelTop);
        imBtnVVTop = (ImageButton) view.findViewById(R.id.imBtnVVTop);
        imBtnVVTop.setOnClickListener(this);
        twTop.setTextColor(getResources().getColor(PROVIDER.getColors().getLinesColor()));

        setVideos();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            if (getArguments() != null) {

            }
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setVideos(){
        if(videos != null && videos.length > 0){
            this.topVideo = findTopVideo();
            if(videos[topVideo].getThumbnail() == null)videos[topVideo].setMyViewListener(this);
            else imBtnVVTop.setBackground(new BitmapDrawable(getResources(),videos[topVideo].getThumbnail()));
            if( gridView != null){
                VideoAdapter videoAdapter = new VideoAdapter(getActivity(),videos);
                videoAdapter.setVideoSelectedListener(this);
                gridView.setAdapter(videoAdapter);
            }
            imBtnVVTop.setOnClickListener(this);
        }
    }
    @Override
    public void onVideoSelected(Video selectedVideo) {
        mListener.onVideoClick(selectedVideo);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imBtnVVTop:
                mListener.onVideoClick(videos[topVideo]);
                break;
        }
    }

    @Override
    public void onVideosReady(Video[] videoList) {
        mListener.saveVideoList(videoList);
        //List<Video> unseen = findUnseenVideos();
        videos = videoList;
        setVideos();
    }

    @Override
    public void onImageDownloadedSuccessful() {
        imBtnVVTop.setBackground(new BitmapDrawable(getResources(),videos[topVideo].getThumbnail()));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener extends VideoTransfer {
       void onVideoClick(Video video);
    }
    private void log(String text){
        VideoMarketingApp.log("Fragment>VideoList> "+text);
    }

    private int findTopVideo(){
        for (int i = 0; i < videos.length; i++) {
            Video vid = videos[i];
            if(vid.getSponsored() == 1){
                log("SponsoredVideo>"+vid.getTitle());
                return i;
            }
        }

        return 0;
    }
    private Video[] findUnseenVideos(){
        if(videos != null){
            List<Video> list = new ArrayList<>();
            for (int i = 0; i < videos.length; i++) {
                if(videos[i].getSeen() == 1){
                    list.add(videos[i]);
                }
            }
            return list.toArray(new Video[list.size()]);
        }
        return null;
    }

}
