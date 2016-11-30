package hr.videomarketing.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import hr.videomarketing.CustomViews.MyEditText;
import hr.videomarketing.Models.BaseModel.GeographicUnits;
import hr.videomarketing.MyWebService.Interfaces.GeoServiceInteraction;
import hr.videomarketing.MyWebService.Services.GeographicUnitsService;
import hr.videomarketing.R;
import hr.videomarketing.VideoMarketingApp;
import hr.videomarketing.MyWebService.Utils.WebServiceException;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;
import static hr.videomarketing.VideoMarketingApp.hideSoftKeyboard;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserSettingsFragmet.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserSettingsFragmet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSettingsFragmet extends Fragment implements GeoServiceInteraction, View.OnTouchListener {
    private static final String ARG_USER = "hr.videomarketing.userparametar";
    private String userParam;
    private OnFragmentInteractionListener mListener;

    private MyEditText etUserName = null;
    private MyEditText etBirthday = null;
    private Spinner spinGender = null;
    private Spinner spinGeoLocation = null;
    private MyEditText etPassword = null;
    private TextView tvChangePassword=null;

    private ImageView appLogo;
    private ImageView providerLogo;

    public UserSettingsFragmet() {
        // Required empty public constructor
    }

    public static UserSettingsFragmet newInstance(String param1) {
        UserSettingsFragmet fragment = new UserSettingsFragmet();
        Bundle args = new Bundle();
        args.putString(ARG_USER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userParam = getArguments().getString(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_settings_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etUserName = (MyEditText)view.findViewById(R.id.metUserName);
        etPassword = (MyEditText)view.findViewById(R.id.metPassword);
        etBirthday = (MyEditText)view.findViewById(R.id.metBirthday);

        spinGeoLocation = (Spinner)view.findViewById(R.id.spGeographicLocations);
        spinGender = (Spinner)view.findViewById(R.id.spGender);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.genders,R.layout.support_simple_spinner_dropdown_item);
        spinGender.setAdapter(adapter);

        appLogo = (ImageView) view.findViewById(R.id.iwAppLogo);
        appLogo.setImageResource(PROVIDER.getLogos().getAppLogoResId());
        providerLogo = (ImageView)view.findViewById(R.id.iwProviderLogo);
        providerLogo.setImageResource(PROVIDER.getLogos().getProviderLogoResId());

        etBirthday.setOnTouchListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            new GeographicUnitsService(this).execute();
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
    public void geoServiceDone(List<GeographicUnits> geographicUnitsList) {
        if (geographicUnitsList.size() > 0) {
            ArrayAdapter geoAdapter = new ArrayAdapter(UserSettingsFragmet.this.getActivity(), R.layout.support_simple_spinner_dropdown_item, geographicUnitsList);
            spinGeoLocation.setAdapter(geoAdapter);
        } else {
            Toast.makeText(getActivity(), getString(R.string.message_geo_location_unsuccessful), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view != null && view.getId()==R.id.userSettingsBackground)hideSoftKeyboard(getActivity());
        else if(view.getId() == etBirthday.getId()){
            final Calendar calendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    calendar.set(Calendar.YEAR,year);
                    calendar.set(Calendar.MONTH,month);
                    calendar.set(Calendar.DAY_OF_MONTH,day);
                    etBirthday.setText(dateFormat.format(calendar.getTime()));
                }
            };

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),onDateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        }
        return false;
    }

    public interface OnFragmentInteractionListener {
        void updateUser();
    }
    private void log(String text){
        VideoMarketingApp.log("Fragment>UserSettings> "+text);
    }
}
