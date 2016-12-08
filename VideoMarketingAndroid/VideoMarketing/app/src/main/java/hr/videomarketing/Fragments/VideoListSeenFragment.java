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
import hr.videomarketing.Utils.VideoAdapter;
import hr.videomarketing.R;
import hr.videomarketing.Utils.VideoClickListener;
import hr.videomarketing.Utils.VideoTransfer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoListSeenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoListSeenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoListSeenFragment extends Fragment implements VideoClickListener {
    private static final String ARG_USER = "hr.videomarketing.videos";

    // TODO: Rename and change types of parameters
    private User user = null;
    private GridView grid = null;
    private Video[] seenVideos = null;
    private OnFragmentInteractionListener mListener;

    public VideoListSeenFragment() {
        // Required empty public constructor
    }


    public static VideoListSeenFragment newInstance(String user) {
        VideoListSeenFragment fragment = new VideoListSeenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.user = User.newInstance(getArguments().getString(ARG_USER));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_list_seen, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        grid = (GridView)view.findViewById(R.id.gridViewSeen);
        seenVideos = mListener.getVideos("seen");
        setGridView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void setGridView() {

        if(seenVideos != null && seenVideos.length > 0){
            VideoAdapter videoAdapter = new VideoAdapter(getActivity(),seenVideos);
            videoAdapter.setVideoSelectedListener(this);
            grid.setAdapter(videoAdapter);
        }
        else{
            TextView txt = new TextView(getActivity());
            txt.setText("Nemate pogledanih videa");
            FrameLayout fr = (FrameLayout) getView().findViewById(R.id.seenCont);
            fr.addView(txt);
        }
    }

    @Override
    public void onVideoSelected(Video selectedVideo) {
        mListener.onSeenVideoClick(selectedVideo);
    }

    public interface OnFragmentInteractionListener extends VideoTransfer {
        void onSeenVideoClick(Video video);
    }
}
