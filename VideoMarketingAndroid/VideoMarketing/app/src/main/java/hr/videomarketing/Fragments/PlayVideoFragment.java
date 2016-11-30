package hr.videomarketing.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.DMVideoView.DMWebVideoView;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.R;
import hr.videomarketing.VideoMarketingApp;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayVideoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayVideoFragment extends Fragment implements DMWebVideoView.Listener{
    private static final String ARG_VIDEO = "hr.videomarketing.video";
    private Video video;

    private EditText etComment;
    private ImageButton imgbtnLike;
    private ImageView iwBack;
    private DMWebVideoView dmWebVideoView;
    private TextView twVideoName;

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
            try {
                this.video = Video.newInstance(new JSONObject(getArguments().getString(ARG_VIDEO)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_video, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dmWebVideoView = (DMWebVideoView) view.findViewById(R.id.dmVideoViewMain);
        dmWebVideoView.setEventListener(this);
        etComment = (EditText)view.findViewById(R.id.etComment);
        imgbtnLike = (ImageButton)view.findViewById(R.id.imbtnLike);
        iwBack  = (ImageView)view.findViewById(R.id.iwIconBack);
        twVideoName = (TextView)view.findViewById(R.id.lblVideoName);


        if(video !=null){
            twVideoName.setText(video.getTitle());

            dmWebVideoView.setControls(false);
            dmWebVideoView.setVideo(video);
            dmWebVideoView.load();
        }
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

    @Override
    public void onEvent(String event) {
        switch (event){
            case "play":
                dmWebVideoView.play();
                break;
            case "start":
                break;
            case "seeking":
                dmWebVideoView.seek(dmWebVideoView.currentTime);
                log("seek");
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void log(String text){
        VideoMarketingApp.log("Fragment>PlayVideo>"+text);
    }
}
