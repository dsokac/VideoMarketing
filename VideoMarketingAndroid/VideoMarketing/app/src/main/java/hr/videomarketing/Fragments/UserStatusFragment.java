package hr.videomarketing.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import hr.videomarketing.CustomViews.MyEditText;
import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.Models.BaseModel.UserStatus;
import hr.videomarketing.MyWebService.Interfaces.OnUserStatusInteractionService;
import hr.videomarketing.MyWebService.Services.UserStatusService;
import hr.videomarketing.R;
import hr.videomarketing.VideoMarketingApp;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;
import static hr.videomarketing.VideoMarketingApp.hideSoftKeyboard;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserStatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserStatusFragment extends Fragment implements View.OnTouchListener,OnUserStatusInteractionService, View.OnClickListener {
    private static final String ARG_USER = "hr.videomarketing.userparametar";
    private User user;
    private EditText etUserName = null;
    private Spinner spinMonths = null;
    private EditText etPointsStatus = null;
    private EditText etSpentPoints = null;
    private ImageButton webShop;
    private ImageButton personalData;
    private OnFragmentInteractionListener mListener;

    private ImageView applogo;
    private ImageView providerLogo;

    public UserStatusFragment() {
        // Required empty public constructor
    }
    public static UserStatusFragment newInstance(String user) {
        UserStatusFragment fragment = new UserStatusFragment();
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
        return inflater.inflate(R.layout.fragment_user_status_layout, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etUserName = (MyEditText) view.findViewById(R.id.etUserName);
        etUserName.setText(user.getUsername());



        etPointsStatus = (MyEditText)view.findViewById(R.id.etPointStatus);
        etPointsStatus.setFocusable(false);
        etSpentPoints = (MyEditText)view.findViewById(R.id.etUsedPoints);

        webShop = (ImageButton)view.findViewById(R.id.imgbtnWebShop);
        webShop.setBackgroundResource(PROVIDER.backgroundPhotoLeft());
        webShop.setOnClickListener(this);
        personalData = (ImageButton)view.findViewById(R.id.imgBtnPersonal);
        personalData.setBackgroundResource(PROVIDER.backgroundPhotoRight());
        personalData.setOnClickListener(this);
        webShop.setColorFilter(getResources().getColor(PROVIDER.getColors().getLinesColor()));
        personalData.setColorFilter(getResources().getColor(PROVIDER.getColors().getLinesColor()));

        applogo = (ImageView)view.findViewById(R.id.iwAppLogo);
        providerLogo = (ImageView)view.findViewById(R.id.iwProviderLogo);

        applogo.setImageResource(PROVIDER.getLogos().getAppLogoResId());
        providerLogo.setImageResource(PROVIDER.getLogos().getProviderLogoResId());
        view.setOnTouchListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        UserStatusService service = new UserStatusService(this,user);
        service.setProgressDialog("Dohvaćam podatke o korisniku");
        service.execute();
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
        hideSoftKeyboard(getActivity());
        return false;
    }

    @Override
    public void onUserStatusService(int earned, int spent) {
        etPointsStatus.setText(Integer.toString(earned));
        etSpentPoints.setText(Integer.toString(spent));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == webShop.getId()){
            mListener.onWebShopClick();
        }
        else if(view.getId() == personalData.getId()){
            mListener.onUserSettingsClick();
        }
    }

    public interface OnFragmentInteractionListener {
        void onWebShopClick();
        void onUserSettingsClick();
    }
    private void log(String text){
        VideoMarketingApp.log("Fragment>UserStatus> "+text);
    }
}
