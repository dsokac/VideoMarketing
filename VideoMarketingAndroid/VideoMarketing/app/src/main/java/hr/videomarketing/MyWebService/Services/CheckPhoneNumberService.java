package hr.videomarketing.MyWebService.Services;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.MyWebService.Interfaces.CheckPhonNuServiceInteraction;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.Utils.WebServiceException;
import hr.videomarketing.MyWebService.VideoMarketingWebService;
import hr.videomarketing.R;

import static hr.videomarketing.MyWebService.Utils.WebServiceException.NO_INTERNET_CONNECTION;

/**
 * Created by bagy on 23.11.2016..
 */

public class CheckPhoneNumberService extends VideoMarketingWebService {
    public final class UrlParam{
        final static String PHONE_NUMBER = "phone_number";
    }

    private final static String MESSAGE_USER_EXISTS = "Korisnik s ovim brojem već postoji";


    private Param[] param;
    CheckPhonNuServiceInteraction myListener;
    public CheckPhoneNumberService(CheckPhonNuServiceInteraction listener, String phoneNumber) {
        super(listener);
        this.myListener = listener;
        String str = phoneNumber.contains("+")?phoneNumber.replace("+",Integer.toString(0)):phoneNumber;
        this.param = new Param[]{new Param(UrlParam.PHONE_NUMBER,str)};
    }
    public CheckPhoneNumberService(CheckPhonNuServiceInteraction listener, String phoneNumber, String progressDiablogMessage){
        super(listener);
        this.myListener = listener;
        this.param = new Param[]{new Param(UrlParam.PHONE_NUMBER,phoneNumber)};
        setProgressDialog(progressDiablogMessage);
    }
    @Override
    public String getVideoMarketingServicePath() {
        return "check_phone_number";
    }

    @Override
    protected void serviceDone(JSONObject result) {
        if(myListener == null){
            onListenerNull(this);
            return;
        }
        try {
            JSONObject jsonObject = result.getJSONObject("data");
            String message = jsonObject.getString("message");
            int success = jsonObject.getInt("success");
            if(success == 1){
                myListener.onPhoneNumberChecked(true,MESSAGE_USER_EXISTS);
            }
            else {
                myListener.onPhoneNumberChecked(false,message);
            }
        }catch (JSONException jsonE){
            onJSONConversionError(jsonE);
        }
    }
    @Override
    public Param[] serviceParam() {
        return param;
    }
}
