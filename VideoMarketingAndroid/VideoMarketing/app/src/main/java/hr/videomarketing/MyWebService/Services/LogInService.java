package hr.videomarketing.MyWebService.Services;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.MyWebService.Interfaces.LogInServiceInteraction;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.Utils.WebServiceException;
import hr.videomarketing.MyWebService.VideoMarketingWebService;

import static hr.videomarketing.MyWebService.Services.LogInService.UrlParam.PASSWORD;
import static hr.videomarketing.MyWebService.Services.LogInService.UrlParam.USERNAME;

/**
 * Created by bagy on 23.11.2016..
 */

public class LogInService extends VideoMarketingWebService {
    protected static class UrlParam{
        public final static String USERNAME = "username";
        public final static String PASSWORD = "password";
    }

    Param[] params;
    LogInServiceInteraction myListener;

    public LogInService(String username, String password, LogInServiceInteraction myListener) {
        setParams(username,password);
        this.myListener = myListener;
    }
    public LogInService(String username, String password, LogInServiceInteraction myListener,String progresDialogMessage) {
        setParams(username,password);
        this.myListener = myListener;
        setProgressDialog(getContextFromListener(myListener),progresDialogMessage);
    }
    @Override
    public String getVideoMarketingServicePath() {
        return "login";
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
            if (success == 1) {
                myListener.onLogInSuccessful(User.newInstance(jsonObject.getString("user")));
            }
            else{
                myListener.onLogInUnsuccessful(jsonObject.getString("message"));
            }
        }catch(JSONException jsonE) {
            onJSONConversionError(jsonE);
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

    public void setParams(String username,String password){
        this.params = new Param[]{new Param(USERNAME,username),new Param(PASSWORD,password)};
    }
}
