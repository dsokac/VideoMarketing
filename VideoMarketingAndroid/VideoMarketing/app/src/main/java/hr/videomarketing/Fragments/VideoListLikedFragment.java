package hr.videomarketing.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.Models.VideoAdapter;
import hr.videomarketing.R;
import hr.videomarketing.Utils.VideoClickListener;
import hr.videomarketing.Utils.VideoTransfer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoListLikedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoListLikedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoListLikedFragment extends Fragment implements VideoClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "hr.videomarket.videos";
    private User user;
    private Video[] likedVideos = null;
    private GridView grid = null;
    private FrameLayout frameLayout;

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
        frameLayout = (FrameLayout)view.findViewById(R.id.container);
        likedVideos = mListener.getVideos("liked");
        setGridView();
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setGridView() {
        if(likedVideos !=null && grid !=null && likedVideos.length>0){
            VideoAdapter videoAdapter = new VideoAdapter(getActivity(),likedVideos);
            videoAdapter.setVideoSelectedListener(this);
            grid.setAdapter(videoAdapter);
        }
        else{
            TextView txt = new TextView(getActivity());
            txt.setText("Nemate lajkanih videa");
            frameLayout.addView(txt);
        }
    }

    @Override
    public void onVideoSelected(Video selectedVideo) {
        mListener.onLikedVideoClick(selectedVideo);
    }

    public interface OnFragmentInteractionListener extends VideoTransfer {
        void onLikedVideoClick(Video video);
    }
}
