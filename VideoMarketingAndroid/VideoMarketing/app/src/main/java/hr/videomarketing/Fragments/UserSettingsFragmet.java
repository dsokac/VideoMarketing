package hr.videomarketing.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import hr.videomarketing.CustomViews.MyEditText;
import hr.videomarketing.Models.BaseModel.GeographicUnits;
import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.MyWebService.Interfaces.GeoServiceInteraction;
import hr.videomarketing.MyWebService.Interfaces.OnUpdateUserService;
import hr.videomarketing.MyWebService.Services.GeographicUnitsService;
import hr.videomarketing.MyWebService.Services.UpdateUserService;
import hr.videomarketing.R;
import hr.videomarketing.Utils.MyFiles;
import hr.videomarketing.Utils.UserDataCheck;
import hr.videomarketing.VideoMarketingApp;

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
public class UserSettingsFragmet extends Fragment implements GeoServiceInteraction, View.OnTouchListener, View.OnClickListener, OnUpdateUserService {
    private static final String ARG_USER = "hr.videomarketing.userparametar";
    private User userParam;
    private User changedData= new User();
    private OnFragmentInteractionListener mListener;

    private MyEditText etUserName = null;
    private MyEditText etBirthday = null;
    private Spinner spinGender = null;
    private Spinner spinGeoLocation = null;
    private MyEditText etPassword = null;
    private TextView tvChangePassword=null;
    private RelativeLayout backButton;
    private Button btnShowPassword;
    private boolean userChanged = false;
    private boolean passwordShown = false;
    private List<GeographicUnits> list= null;
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
            userParam = User.newInstance(getArguments().getString(ARG_USER));
        }
        setHasOptionsMenu(true);
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
        etPassword = (MyEditText)view.findViewById(R.id.etPasswordUserSettings);
        etBirthday = (MyEditText)view.findViewById(R.id.metBirthday);
        btnShowPassword = (Button)view.findViewById(R.id.imBtnShowPasswordUserSettings);
        btnShowPassword.setOnClickListener(this);

        spinGeoLocation = (Spinner)view.findViewById(R.id.spGeographicLocations);
        spinGender = (Spinner)view.findViewById(R.id.spGender);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.genders,R.layout.support_simple_spinner_dropdown_item);
        spinGender.setAdapter(adapter);

        appLogo = (ImageView) view.findViewById(R.id.iwAppLogo);
        appLogo.setImageResource(PROVIDER.getLogos().getAppLogoResId());
        providerLogo = (ImageView)view.findViewById(R.id.iwProviderLogo);
        providerLogo.setImageResource(PROVIDER.getLogos().getProviderLogoResId());
        backButton = (RelativeLayout)view.findViewById(R.id.relLayoutBack);
        backButton.setBackgroundColor(getResources().getColor(PROVIDER.getColors().getBottomBarColor()));
        backButton.setOnClickListener(this);
        etBirthday.setFocusable(false);
        etBirthday.setOnClickListener(this);
        setUser(userParam);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_save){
            log("toolbar>action_save");
            User user = changeUser();
            if (!user.isNullObject()){
                updateUser(user);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        mListener.setBottomBarVisibility(false);
        mListener.setSaveIconVisibility(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        mListener.setBottomBarVisibility(true);
        mListener.setSaveIconVisibility(false);
    }

    @Override
    public void onPause() {
        super.onPause();
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
            list = geographicUnitsList;
            spinGeoLocation.setSelection(getGeoUnitIndex(userParam.getGeographicUnits()));
        } else {
            Toast.makeText(getActivity(), getString(R.string.message_geo_location_unsuccessful), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view != null && view.getId()==R.id.userSettingsBackground)hideSoftKeyboard(getActivity());
        return false;
    }

    private User changeUser(){
        User changed = userParam.clone();
        userChanged = false;
        String data = etUserName.getText().toString();
        Drawable errorIcon = getResources().getDrawable(R.drawable.ic_error);
        errorIcon.setBounds(0,0,errorIcon.getIntrinsicWidth(),errorIcon.getIntrinsicHeight());
        if(!UserDataCheck.getInstance().checkUserName(data)){
            etUserName.setError(getResources().getString(R.string.input_incorrect_name),errorIcon);
            return new User();
        }
        changed.setUsername(data);

        data = etBirthday.getText().toString();
        if(!UserDataCheck.getInstance().checkBirthday(data)){
            etBirthday.setError(getResources().getString(R.string.input_incorrect_birthday),errorIcon);
            return new User();
        }
        changed.setBirthday(data);
        GeographicUnits geo = (GeographicUnits)spinGeoLocation.getSelectedItem();
        if(geo == null)return new User();
        changed.setGeographicUnits(geo);
        data = etPassword.getText().toString();
        if(!UserDataCheck.getInstance().checkPassword(data)){
            etPassword.setError(getResources().getString(R.string.input_incorrect_password),errorIcon);
            return new User();
        }
        changed.setPassword(data);
        log("user changed object>"+changed.toJSON());
        log("user compared with>"+userParam.toJSON());
        if(!changed.equals(userParam))userChanged = true;
        return changed;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relLayoutBack:
                if(userChanged){
                    AlertDialog.Builder build = new AlertDialog.Builder(getActivity(), android.app.AlertDialog.THEME_HOLO_LIGHT);
                    build.setTitle(getResources().getString(R.string.message_user_update));
                    build.setTitle(getResources().getString(R.string.message_warning_title));
                    build.setPositiveButton(getResources().getString(R.string.alert_dialog_save_changes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            updateUser(changeUser());
                            getActivity().onBackPressed();
                        }
                    });
                    build.setNegativeButton(getResources().getString(R.string.alert_dialog_dont_save_changes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().onBackPressed();
                        }
                    });
                    AlertDialog alert = build.create();
                    alert.show();
                }
                else getActivity().onBackPressed();
                break;
            case R.id.metBirthday:
                final Calendar calendar = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        etBirthday.setText(dateFormat.format(calendar.getTime()));
                    }
                };

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.app.AlertDialog.THEME_HOLO_LIGHT, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                }
                break;
            case R.id.imBtnShowPasswordUserSettings:
                    if(!passwordShown){
                        passwordShown = true;
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        btnShowPassword.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    }
                    else {
                        passwordShown = false;
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        btnShowPassword.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                    }
                break;
        }
    }

    private void updateUser(User changedUser) {
        if(!changedUser.isNullObject() && userChanged){
            if(userParam.getPassword().equals(changedUser.getPassword()))changedUser.setPassword("");
            UpdateUserService service = new UpdateUserService(this, changedUser);
            service.setProgressDialog("Promijena podataka");
            service.execute();
            userChanged = false;
            changedData = changedUser;
            if(changedUser.getPassword().equals(""))changedData.setPassword(userParam.getPassword());
        }
    }

    @Override
    public void onUserUpdate(boolean success, String message) {
        log("user update done>"+message);
        if(success){
            Toast.makeText(getActivity(),"Podaci uspiješno ažurirani",Toast.LENGTH_SHORT).show();
            userParam = changedData;
            MyFiles.getInstance().writeInFile(getActivity(), MyFiles.Files.USER_DATA_FILE,userParam.toJSON());
            mListener.userUpdated(userParam);
            setUser(userParam);
        }else{
            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            setUser(userParam);
        }
    }

    public interface OnFragmentInteractionListener {
        void setBottomBarVisibility(boolean visibility);
        void setSaveIconVisibility(boolean visibility);
        void userUpdated(User user);
    }
    private void log(String text){
        VideoMarketingApp.log("Fragment>UserSettings> "+text);
    }
    private int getGeoUnitIndex(GeographicUnits unit){
        if(list != null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getId() == unit.getId()){
                    return i;
                }
            }
        }
        return -1;
    }
    private void setUser(User user){
        etUserName.setText(user.getUsername());
        etPassword.setText(user.getPassword());
        etBirthday.setText(user.getBirthday());
        spinGeoLocation.setSelection(getGeoUnitIndex(user.getGeographicUnits()));
        spinGender.setSelection(user.getGender().equals("M")?0:1);
    }
}
