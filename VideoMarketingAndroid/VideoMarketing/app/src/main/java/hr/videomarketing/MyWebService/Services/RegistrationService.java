package hr.videomarketing.MyWebService.Services;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.MyWebService.Interfaces.RegistrationServiceInteraction;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.Utils.WebServiceException;
import hr.videomarketing.MyWebService.VideoMarketingWebService;

/**
 * Created by bagy on 23.11.2016..
 */

public class RegistrationService extends VideoMarketingWebService {
    private Param[] params;
    RegistrationServiceInteraction myListener;
    public RegistrationService(RegistrationServiceInteraction myListener,User user){
        List<Param> list = user.toParam();
        this.myListener = myListener;
        this.params = list.toArray(new Param[list.size()]);
    }
    public RegistrationService(RegistrationServiceInteraction myListener,User user,String progressDialogMessage){
        List<Param> list = user.toParam();
        this.myListener = myListener;
        this.params = list.toArray(new Param[list.size()]);
        setProgressDialog(getContextFromListener(myListener),progressDialogMessage);
    }
    @Override
    public String getVideoMarketingServicePath() {
        return "registration";
    }

    @Override
    protected void serviceDone(JSONObject result) {
        if(myListener == null){
            onListenerNull(this);
            return;
        }
        try {
            JSONObject jsonObject = result.getJSONObject("data");
            int success = jsonObject.getInt("success");
            String message = jsonObject.getString("message");
            if(success == 1){
                myListener.onRegistraionService(true,message);
            } else {
                myListener.onRegistraionService(false,message);
            }
        }catch (JSONException json){
            onJSONConversionError(json);
        }
    }

    @Override
    protected Context getContext() {
        return getContextFromListener(myListener);
    }

    @Override
    public Param[] serviceParam() {
        return params;
    }
}