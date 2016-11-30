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
import java.util.List;

import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.Models.VideoAdapter;
import hr.videomarketing.MyWebService.Interfaces.VideoListInteractionService;
import hr.videomarketing.MyWebService.Services.VideoListService;
import hr.videomarketing.R;
import hr.videomarketing.Utils.VideoClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoListLikedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoListLikedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoListLikedFragment extends Fragment implements VideoListInteractionService,VideoClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "hr.videomarket.videos";
    private User user;
    private List<Video> likedVideos = null;
    private GridView grid = null;

    private OnFragmentInteractionListener mListener;

    public VideoListLikedFragment() {
        // Required empty public constructor
    }
    public static VideoListLikedFragment newInstance(String user) {
        VideoListLikedFragment fragment = new VideoListLikedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = User.newInstance(getArguments().getString(ARG_USER));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_list_liked, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        grid = (GridView)view.findViewById(R.id.gridLiked);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            if(this.user != null)
            {
                new VideoListService(this.user.getId(),this,getResources().getString(R.string.message_get_videos)).execute();
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
        if(videoList !=null && videoList.size()>0){
            this.likedVideos = new ArrayList<>();
            for (Video vid:videoList) {
                if(vid.getUserLike() == 1){
                    this.likedVideos.add(vid);
                }
            }
            setGridView();
        }
        else {
            TextView txt = new TextView(getActivity());
            txt.setText("Nemate lajkanih videa");
            grid.addView(txt);
        }
    }

    private void setGridView() {
        if(likedVideos !=null){
            Video[] vid = likedVideos.toArray(new Video[likedVideos.size()]);
            VideoAdapter videoAdapter = new VideoAdapter(getActivity(),vid);
            videoAdapter.setVideoSelectedListener(this);
            grid.setAdapter(videoAdapter);
        }
    }

    @Override
    public void onVideoSelected(Video selectedVideo) {
        mListener.onLikedVideoClick(selectedVideo);
    }

    public interface OnFragmentInteractionListener {
        void onLikedVideoClick(Video video);
    }
}
