package hr.videomarketing.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import hr.videomarketing.CustomViews.MyEditText;
import hr.videomarketing.Models.BaseModel.GeographicUnits;
import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.MyWebService.Interfaces.CheckPhonNuServiceInteraction;
import hr.videomarketing.MyWebService.Interfaces.GeoServiceInteraction;
import hr.videomarketing.MyWebService.Interfaces.RegistrationServiceInteraction;
import hr.videomarketing.MyWebService.Services.CheckPhoneNumberService;
import hr.videomarketing.MyWebService.Services.GeographicUnitsService;
import hr.videomarketing.MyWebService.Services.RegistrationService;
import hr.videomarketing.R;
import hr.videomarketing.Utils.MyFiles;
import hr.videomarketing.Utils.UserDataCheck;
import hr.videomarketing.VideoMarketingApp;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationFragment.OnRegistrationFragmentInteraction} interface
 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment implements Button.OnClickListener, RegistrationServiceInteraction,
        GeoServiceInteraction,View.OnFocusChangeListener, CheckPhonNuServiceInteraction {
    private final static String ARG_PHONENUMBER ="hr.videomarketing.phonenumber" ;
    private OnRegistrationFragmentInteraction mListener;
    private String phoneNumber;
    private EditText etBirthday = null;
    private EditText etName = null;
    private EditText etUserName = null;
    private EditText etPasswrod = null;
    private EditText etEmail = null;
    private Spinner spinGeoLocation = null;
    private Spinner spinGender = null;
    private ImageButton btnReg = null;
    private EditText lblLogIn = null;
    private ImageView iwLogoApp = null;
    private ImageView iwLogoOperator = null;
    private EditText etPhoneNumber = null;
    private MyEditText etPasswordCheck = null;
    private Button btnShowPassword = null;
    private boolean passwordShown = false;
    private Map<EditText,Boolean> inputs;
    private Drawable errorIcon;
    private User registeredUser;
    public RegistrationFragment() {
        // Required empty public constructor
    }
    public static RegistrationFragment newInstance(String phoneNumber) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle extras = new Bundle();
        extras.putString(ARG_PHONENUMBER,phoneNumber);
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.phoneNumber = getArguments().getString(ARG_PHONENUMBER);
            log("Fragment>Registration>PhoneNUmber"+phoneNumber);
        }
        new GeographicUnitsService(this,getString(R.string.message_get_geo_locations)).execute();
        errorIcon = getResources().getDrawable(R.drawable.ic_error);
        errorIcon.setBounds(0,0,errorIcon.getIntrinsicWidth(),errorIcon.getIntrinsicHeight());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iwLogoApp = (ImageView)view.findViewById(R.id.iwAppLogo);
        iwLogoOperator = (ImageView)view.findViewById(R.id.iwNetworkOperLogo);
        inputs = new HashMap<>();

        etName = (MyEditText)view.findViewById(R.id.etName);
        inputs.put(etName,false);
        etName.setOnFocusChangeListener(this);

        etEmail = (MyEditText)view.findViewById(R.id.etEmail);
        inputs.put(etEmail,false);
        etEmail.setOnFocusChangeListener(this);

        etUserName = (MyEditText)view.findViewById(R.id.etUserName);
        etUserName.setOnFocusChangeListener(this);
        inputs.put(etUserName,false);

        etPasswrod = (MyEditText)view.findViewById(R.id.etPassword);
        etPasswrod.setOnFocusChangeListener(this);
        inputs.put(etPasswrod,false);

        etPasswordCheck = (MyEditText)view.findViewById(R.id.etPasswordCheck);
        etPasswordCheck.setOnFocusChangeListener(this);
        inputs.put(etPasswordCheck,false);


        btnShowPassword = (Button)view.findViewById(R.id.imBtnShowPassword);
        btnShowPassword.setOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                etBirthday.setText(dateFormat.format(calendar.getTime()));
                replace(etBirthday,true);
            }
        };

        etBirthday = (MyEditText)view.findViewById(R.id.etBirthday);
        inputs.put(etBirthday,false);
        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.app.AlertDialog.THEME_HOLO_LIGHT,onDateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                }
            }
        } );

        spinGender = (Spinner)view.findViewById(R.id.spinGender);

        spinGeoLocation = (Spinner)view.findViewById(R.id.spinGeoUnits);

        btnReg = (ImageButton)view.findViewById(R.id.ibDoRegistratin);
        btnReg.setBackgroundColor(getResources().getColor(PROVIDER.getButtonColors()));
        btnReg.setImageResource(PROVIDER.getLogos().getButtonCheckLogoResId());
        btnReg.setOnClickListener(this);
        iwLogoOperator = (ImageView)view.findViewById(R.id.iwNetworkOperLogo);
        iwLogoOperator.setImageResource(PROVIDER.getLogos().getProviderLogoResId());
        iwLogoApp = (ImageView)view.findViewById(R.id.iwAppLogo);
        iwLogoApp.setImageResource(PROVIDER.getLogos().getAppLogoResId());
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),R.array.genders,R.layout.support_simple_spinner_dropdown_item);
        spinGender.setAdapter(adapter);

        etPhoneNumber = (EditText)view.findViewById(R.id.etPhoneNumber);
        etPhoneNumber.setOnFocusChangeListener(this);
        inputs.put(etPhoneNumber,false);

        if(this.phoneNumber != null & !this.phoneNumber.equals("")){
            checkPhoneNumberInDb(this.phoneNumber);
            etPhoneNumber.setText(this.phoneNumber);
            etPhoneNumber.setFocusable(false);
            replace(etPhoneNumber,true);
        }


        lblLogIn = (MyEditText)view.findViewById(R.id.etLogIn);
        lblLogIn.setClickable(true);
        lblLogIn.setFocusable(false);
        lblLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object object = MyFiles.getInstance().readFromFIle(getActivity(), MyFiles.Files.USER_DATA_FILE);
                User user = object instanceof User && object != null?(User)object:null;
                mListener.redirectToLogIn(user !=null?user.toJSON():"");
            }
        });
        ScrollView layout = (ScrollView) view.findViewById(R.id.registration_scrollview_layout);
        layout.setFocusableInTouchMode(true);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                VideoMarketingApp.hideSoftKeyboard(getActivity());
                return false;
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegistrationFragmentInteraction) {
            mListener = (OnRegistrationFragmentInteraction) context;
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
        if(view.getId() == R.id.ibDoRegistratin){
            User user = isInputCorrect();
            if(user != null){
                registeredUser = user;
                log("Conversion: user to string: "+registeredUser.toJSON());
                new RegistrationService(this,registeredUser,getResources().getString(R.string.message_registration)).execute();
            }
            else{
                Toast.makeText(getActivity(),getResources().getString(R.string.message_user_fields_empty),Toast.LENGTH_LONG).show();
            }
        }
        else if(view.getId() == btnShowPassword.getId()){
            if(!passwordShown){
                passwordShown = true;
                etPasswrod.setInputType(InputType.TYPE_CLASS_TEXT);
                btnShowPassword.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            }
            else {
                passwordShown = false;
                etPasswrod.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btnShowPassword.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            }
        }
    }


    @Override
    public void geoServiceDone(List<GeographicUnits> geographicUnitsList) {
        if (geographicUnitsList.size() > 0) {
            ArrayAdapter geoAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, geographicUnitsList);
            spinGeoLocation.setAdapter(geoAdapter);
        } else {
            Toast.makeText(getActivity(), getString(R.string.message_geo_location_unsuccessful), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRegistraionService(boolean success, String message) {
        if(!success){
            //Unsuccessful registration
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message);
            builder.setTitle(getString(R.string.unsuccessful_registration));
            builder.setPositiveButton(getString(R.string.dialog_btn_text_logIn), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    mListener.redirectToLogIn(registeredUser != null?registeredUser.toJSON():"");
                }
            });
            builder.setNegativeButton(getString(R.string.dialog_btn_text_re_registration), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else{
            registeredUser.setLogedIn(false);
            if(MyFiles.getInstance().writeInFile(getActivity(),MyFiles.Files.USER_DATA_FILE,registeredUser.toJSON())){
                log("write user in file successful"+registeredUser.toJSON());
            }
            else{
                log("write user in file unsuccessful");
            }
            mListener.redirectToLogIn(registeredUser.toJSON());
        }
    }


    private User isInputCorrect(){
        if(spinGeoLocation.getSelectedItem() == null){
            log("There is no geo locations");
            return null;
        }
        if(inputs.containsValue(false)){
            log("Something isnt true");
            log("inputs size>"+inputs.size());
            for (Map.Entry<EditText,Boolean> entry:inputs.entrySet()) {
                EditText k = entry.getKey();
                Boolean v = entry.getValue();
                if(!v){
                    k.setError(getString(R.string.input_incorrect_all),errorIcon);
                }
            }
            return null;
        }
        //Reading user data
        User newUser = new User();
        if(inputs.get(etName)){
            String[] userNameSurname = etName.getText().toString().split(" ");
            newUser.setName(userNameSurname[0].trim());
            String surname = "";
            for (int i = 1; i < userNameSurname.length; i++) {
                surname +=userNameSurname[i];
                if(userNameSurname.length>2 && i < (userNameSurname.length-1))surname += "-";
            }
            newUser.setSurname(surname.trim());
        }

        this.phoneNumber = etPhoneNumber.getText().toString().trim();
        newUser.setPhoneNumber(this.phoneNumber);
        newUser.setUsername(etUserName.getText().toString().trim());
        newUser.setEmail(etEmail.getText().toString().trim());
        newUser.setPassword(etPasswrod.getText().toString().trim());
        newUser.setGender(spinGender.getSelectedItem().toString().trim());

        newUser.setGeographicUnits((GeographicUnits)spinGeoLocation.getSelectedItem());
        newUser.setBirthday(etBirthday.getText().toString().trim());
        return newUser;
    }
    //split input string on whitespace, on index 0 is name 1 is surname


    @Override
    public void onFocusChange(View view, boolean b) {
            switch (view.getId()){
                case R.id.etName:
                    if(!UserDataCheck.getInstance().checkName(etName.getText().toString())){
                        etName.setError(getResources().getString(R.string.input_incorrect_name),errorIcon);
                        replace(etName,false);
                    }else{
                        replace(etName,true);
                    }
                    break;
                case R.id.etBirthday:
                    if(!UserDataCheck.getInstance().checkBirthday(etBirthday.getText().toString())){
                        etBirthday.setError(getResources().getString(R.string.input_incorrect_birthday),errorIcon);
                        replace(etBirthday,false);
                    }
                    else{
                        replace(etBirthday,true);
                    }
                    break;
                case R.id.etEmail:
                    if(!UserDataCheck.getInstance().checkEmail(etEmail.getText().toString())){
                        etEmail.setError(getResources().getString(R.string.input_incorrect_email),errorIcon);
                        replace(etEmail,false);
                    }else{
                        replace(etEmail,true);
                    }
                    break;
                case R.id.etPassword:
                    if(!UserDataCheck.getInstance().checkPassword(etPasswrod.getText().toString())){
                        etPasswrod.setError(getResources().getString(R.string.input_incorrect_password),errorIcon);
                        replace(etPasswrod,false);
                    }
                    else{
                        replace(etPasswrod,true);
                    }
                    break;
                case R.id.etUserName:
                    if(!UserDataCheck.getInstance().checkUserName(etUserName.getText().toString())){
                        etUserName.setError(getResources().getString(R.string.input_incorrect_username),errorIcon);
                        replace(etUserName,false);
                    }
                    else{
                        replace(etUserName,true);
                    }
                    break;
                case R.id.etPasswordCheck:
                    String password = etPasswrod.getText().toString();
                    String check = etPasswordCheck.getText().toString();
                    if(check.equals(password)){
                        replace(etPasswordCheck,true);
                    }else{
                        etPasswordCheck.setError(getString(R.string.input_incorrect_password_check),errorIcon);
                        replace(etPasswordCheck,false);
                    }
                    break;
                case R.id.etPhoneNumber:
                    String number = etPhoneNumber.getText().toString();
                    if(UserDataCheck.getInstance().checkPhoneNumber(number)){
                        checkPhoneNumberInDb(number);
                    }else{
                        etPhoneNumber.setError(getResources().getString(R.string.input_incorrect_phone_number),errorIcon);
                        replace(etPhoneNumber,false);
                    }
                    break;
                default:
                    log("focus changed, view not found");
            }
    }
    private void checkPhoneNumberInDb(String phonenumber){
        log("Phone number>"+phonenumber);
        if(phonenumber != null && !phonenumber.equals("")){
            String foo = phonenumber.replace("+",Integer.toString(0));
            log("foo>"+foo);
            new CheckPhoneNumberService(this,foo,getString(R.string.action_check_number)).execute();
        }

    }

    @Override
    public void onPhoneNumberChecked(boolean exist, final String message) {
        if(exist){
            AlertDialog.Builder build = new AlertDialog.Builder(getActivity(), android.app.AlertDialog.THEME_HOLO_LIGHT);
            build.setMessage(message);
            build.setTitle(getString(R.string.message_warning_title));
            build.setPositiveButton(getString(R.string.dialog_btn_text_logIn), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mListener.redirectToLogIn("");
                }
            });
            build.setNegativeButton(getString(R.string.dialog_btn_permission_denied), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getActivity().finish();
                }
            });
            AlertDialog alert = build.create();
            alert.show();
        }
        else{
            replace(etPhoneNumber,true);
        }
    }

    public interface OnRegistrationFragmentInteraction {
        void redirectToLogIn(String data);
    }
    private void log(String text){
        VideoMarketingApp.log("Fragment>Registration>"+text);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void replace(EditText et, boolean v){
        if(inputs.containsKey(et) && inputs.get(et) != v){
            log("Replacing>"+et.getId());
            inputs.remove(et);
            inputs.put(et,v);
        }
    }
}
