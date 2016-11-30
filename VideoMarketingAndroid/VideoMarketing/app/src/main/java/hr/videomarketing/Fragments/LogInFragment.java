package hr.videomarketing.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import hr.videomarketing.CustomViews.MyEditText;
import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.MyWebService.Interfaces.LogInServiceInteraction;
import hr.videomarketing.MyWebService.Services.LogInService;
import hr.videomarketing.R;
import hr.videomarketing.Utils.MyFiles;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;
import static hr.videomarketing.VideoMarketingApp.hideSoftKeyboard;
import static hr.videomarketing.VideoMarketingApp.log;


public class LogInFragment extends Fragment implements Button.OnClickListener, LogInServiceInteraction {
    private final static String ARG_USER = "hr.videomarketing.userparametar";
    private User user;
    // TODO: Rename and change types of parameters
    private MyEditText etUserName = null;
    private MyEditText etPassword = null;
    private TextView twLostPassword = null;
    private MyEditText twDoRegistration = null;
    private Button btnLogIn = null;
    private ImageView iwAppLogo=null;
    private ImageView iwNetworkProvider=null;


    private OnFragmentInteractionListener mListener;

    public LogInFragment() {

    }
    public static LogInFragment newInstance(String user) {
        Bundle args = new Bundle();
        LogInFragment fragment = new LogInFragment();
        args.putString(ARG_USER,user);
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
        return inflater.inflate(R.layout.fragment_log_in_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etUserName = (MyEditText)view.findViewById(R.id.etUserName);
        etPassword = (MyEditText)view.findViewById(R.id.etLogInPassword);
        twDoRegistration = (MyEditText) view.findViewById(R.id.twDoRegistration);
        twDoRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log("action_RedirectToRegistration");
                mListener.redirectToRegistration();
            }
        });


        btnLogIn = (Button)view.findViewById(R.id.btnLogIn);
        btnLogIn.setBackgroundColor(getResources().getColor(PROVIDER.getButtonColors()));
        btnLogIn.setOnClickListener(this);

        iwAppLogo = (ImageView)view.findViewById(R.id.iwAppLogo);
        iwAppLogo.setImageResource(PROVIDER.getLogos().getAppLogoResId());

        iwNetworkProvider = (ImageView) view.findViewById(R.id.iwNetworkProviderLogo);
        iwNetworkProvider.setImageResource(PROVIDER.getLogos().getProviderLogoResId());

        final ScrollView layout = (ScrollView)view.findViewById(R.id.logIn_scrollview_layout);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftKeyboard(getActivity());
                return false;
            }
        });
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
    public void onClick(View view) {
        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        if(username.equals("") || username.equals(" ") || password.equals("") || password.equals(" ")){
            Toast.makeText(getContext(),R.string.message_lbl_corruptedInputData,Toast.LENGTH_LONG).show();
        }
        else{
            LogInService lis = new LogInService(username,password,this);
            lis.setProgressDialog(getActivity(),getResources().getString(R.string.message_action_logIn));
            lis.execute();
        }
    }

    @Override
    public void onLogInUnsuccessful(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLogInSuccessful(User user) {
        MyFiles.writeInFile(getActivity(), MyFiles.Files.USER_DATA_FILE, user.toString());
        user.logIn(getActivity());
        mListener.userLogInSuccessful();
    }


    public interface OnFragmentInteractionListener {
        void redirectToRegistration();
        void userLogInSuccessful();
    }
}
