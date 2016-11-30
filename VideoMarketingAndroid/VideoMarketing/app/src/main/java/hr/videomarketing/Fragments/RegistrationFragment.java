package hr.videomarketing.Fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
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
import java.util.List;
import java.util.Locale;

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
        GeoServiceInteraction,CheckPhonNuServiceInteraction,View.OnFocusChangeListener{
    private OnRegistrationFragmentInteraction mListener;
    private String phoneNumber="";
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
    private User registeredUser;
    public RegistrationFragment() {
        // Required empty public constructor
    }
    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        etName = (MyEditText)view.findViewById(R.id.etName);
        etEmail = (MyEditText)view.findViewById(R.id.etEmail);
        etUserName = (MyEditText)view.findViewById(R.id.etUserName);
        etPasswrod = (MyEditText)view.findViewById(R.id.etLogInPassword);

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

        etBirthday = (MyEditText)view.findViewById(R.id.etBirthday);
        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(),onDateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
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

        lblLogIn = (MyEditText)view.findViewById(R.id.etLogIn);
        lblLogIn.setClickable(true);
        lblLogIn.setFocusable(false);
        lblLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object object = MyFiles.readFromFIle(getActivity(), MyFiles.Files.USER_DATA_FILE);
                User user = object instanceof User && object != null?(User)object:null;
                mListener.redirectToLogIn(user !=null?user.toString():"");
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

        new GeographicUnitsService(this,getResources().getString(R.string.message_get_geo_locations)).execute();
        requestPermission();
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
                log("Conversion: user to string: "+registeredUser.toString());
                new RegistrationService(this,registeredUser,getResources().getString(R.string.message_registration)).execute();
            }
            else{
                Toast.makeText(getActivity(),getResources().getString(R.string.message_user_fields_empty),Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onPhoneNumberChecked(boolean exist, String message) {
        if(exist){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getResources().getString(R.string.message_user_exists));
            builder.setTitle(getResources().getString(R.string.message_warning_title));
            builder.setPositiveButton(getResources().getString(R.string.message_log_in), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Object obj = MyFiles.readFromFIle(getActivity(), MyFiles.Files.USER_DATA_FILE);
                    String data = obj !=null?obj.toString():null;
                    mListener.redirectToLogIn(data);
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.dialog_btn_permission_denied), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getActivity().finish();
                }
            });
            AlertDialog alrt = builder.create();
            alrt.show();
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
                    mListener.redirectToLogIn(registeredUser != null?registeredUser.toString():"");
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
            if(MyFiles.writeInFile(getActivity(),MyFiles.Files.USER_DATA_FILE,registeredUser.toString())){
                registeredUser.logOut(getActivity());
            }
            else{
                log("write user in file unsuccessful");
            }
            mListener.redirectToLogIn(registeredUser.toString());
        }
    }


    private User isInputCorrect(){
        if(this.phoneNumber.equals("")){
            Toast.makeText(getActivity(),getResources().getString(R.string.message_alert_dialog_allowPermission),Toast.LENGTH_LONG).show();
            requestPermission();
        }
        //TODO:provjeriti jel ispravan format emaila, jel lozinka ima 8+ znakova...
        if(etName.getText().toString().equals("") || etEmail.getText().toString().equals("") || etUserName.getText().toString().equals("")|| etPasswrod.getText().toString().equals("") || etBirthday.getText().toString().equals(""))
        {
            log("Name: "+etName.getText().toString());
            log("Email: "+etEmail.getText().toString());
            log("Username: "+etUserName.getText().toString());
            log("Password: "+etPasswrod.getText().toString());
            log("Phone Number:: "+phoneNumber);
            return null;
        }
        //Reading user data
        User newUser = new User();
        String[] userNameSurname = getNameSurnameArray(etName.getText().toString());
        if(userNameSurname.length >= 1){
            newUser.setName(userNameSurname[0]);
            if(userNameSurname.length > 1){
                String surname = "";
                for (int i = 1; i < userNameSurname.length; i++) {
                    surname +=userNameSurname[i];
                    if(userNameSurname.length>2 && i < (userNameSurname.length-1))surname += "-";
                }
                newUser.setSurname(surname);
            }
        }
        else {
            Toast.makeText(getContext(), getResources().getString(R.string.message_name_surname_format), Toast.LENGTH_LONG).show();
            return null;
        }
        newUser.setPhoneNumber(this.phoneNumber);
        newUser.setUsername(etUserName.getText().toString());
        newUser.setEmail(etEmail.getText().toString());
        newUser.setPassword(etPasswrod.getText().toString());
        newUser.setGender(spinGender.getSelectedItem().toString());
        newUser.setGeographicUnits((GeographicUnits)spinGeoLocation.getSelectedItem());
        newUser.setBirthday(etBirthday.getText().toString());
        return newUser;
    }
    //split input string on whitespace, on index 0 is name 1 is surname
    private String[] getNameSurnameArray(String input){
        if(etName !=null){
            String[] data = input.split(" ");
            if(data.length>0) {
                return data;
            }
        }
        return null;
    }
    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, VideoMarketingApp.PERMISSION_READ_PHONE_STATE);
        }
        else {
            TelephonyManager manager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            this.phoneNumber=manager.getLine1Number();
            new CheckPhoneNumberService(this,this.phoneNumber).execute();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case VideoMarketingApp.PERMISSION_READ_PHONE_STATE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    TelephonyManager manager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    this.phoneNumber = manager.getLine1Number();
                    new CheckPhoneNumberService(this,this.phoneNumber);
                }
                else{
                    //TODO: warn to must enable it...ask again-partly done
                    log("");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getString(R.string.message_warning_title));
                    builder.setMessage(getResources().getString(R.string.message_alert_dialog_allowPermission));
                    builder.setPositiveButton(getString(R.string.dialog_btn_ask_permission_again), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            requestPermission();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.dialog_btn_permission_denied), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        //TODO:check outcheck input on lost focus
        if(!b){
            switch (view.getId()){
                case R.id.etName:
                    break;
                case R.id.etBirthday:
                    break;
                case R.id.etEmail:
                    break;
                case R.id.etLogInPassword:
                    break;
                case R.id.etUserName:
                    break;
                default:
                    log("focus changed, view not found");
            }
        }
    }

    public interface OnRegistrationFragmentInteraction {
        void redirectToLogIn(String data);
    }
    private void log(String text){
        VideoMarketingApp.log("Fragment>Registration>"+text);
    }
}
