package hr.videomarketing.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.videomarketing.DMVideoView.DMWebVideoView;
import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.Models.VideoAdapter;
import hr.videomarketing.MyWebService.Interfaces.VideoListInteractionService;
import hr.videomarketing.MyWebService.Services.VideoListService;
import hr.videomarketing.R;
import hr.videomarketing.Utils.VideoClickListener;
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
public class VideoListFragment extends Fragment implements VideoListInteractionService, VideoClickListener{

    private static final String ARG_USER = "hr.videomarketing.user_extras";
    private User user;
    private TextView twTop;
    private OnFragmentInteractionListener mListener;
    private DMWebVideoView mVideoViewTop = null;
    private GridView gridView = null;
    private Video[] videos;

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
    }

    @Override
    public void onStart() {
        super.onStart();
        if(videos == null || videos.length ==0){
            user = User.newInstance(getArguments().getString(ARG_USER));
            if(user !=null){
                new VideoListService(user.getId(),this,getResources().getString(R.string.message_get_videos)).execute();
            }
        }
        else{
            onVideosReady(new ArrayList<>(Arrays.asList(videos)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_list_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = (GridView) view.findViewById(R.id.gvVideoList);
        twTop = (TextView) view.findViewById(R.id.twLabelTop);
        mVideoViewTop = (DMWebVideoView) view.findViewById(R.id.topVideo);
        twTop.setTextColor(getResources().getColor(PROVIDER.getColors().getLinesColor()));

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

    @Override
    public void onVideosReady(List<Video> videoList) {
        if(videoList != null && videoList.size() > 0){
            Video top = findTopVideo(videoList);
            if(top != null)mVideoViewTop.setVideo(top);
            else {
                mVideoViewTop.setVideo(videoList.get(videoList.size()-1));
                }
            mVideoViewTop.load();
            if( gridView != null){
                videos = videoList.toArray(new Video[videoList.size()]);
                VideoAdapter videoAdapter = new VideoAdapter(getActivity(),videos);
                videoAdapter.setVideoSelectedListener(this);
                gridView.setAdapter(videoAdapter);
            }
        }
    }
    @Override
    public void onVideoSelected(Video selectedVideo) {
        mListener.onVideoClick(selectedVideo);
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
    public interface OnFragmentInteractionListener{
       void onVideoClick(Video video);
    }
    private void log(String text){
        VideoMarketingApp.log("Fragment>VideoList> "+text);
    }

    private Video findTopVideo(List<Video> videoList){
        for (int i = 0; i < videoList.size(); i++) {
            Video vid = videoList.get(i);
            if(vid.getSponsored() == 1){
                //videoList.remove(i);
                return vid;
            }
        }

        return null;
    }
}
