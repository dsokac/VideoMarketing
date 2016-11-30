package hr.videomarketing.MyWebService.Services;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.MyWebService.Interfaces.OnUpdateUserService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.Utils.WebServiceException;
import hr.videomarketing.MyWebService.VideoMarketingWebService;

/**
 * Created by bagy on 24.11.2016..
 */

public class UpdateUserService extends VideoMarketingWebService {
    Param[] params;
    OnUpdateUserService myListener;
    public UpdateUserService(OnUpdateUserService l, User user){
        this.myListener = l;
        setUser(user);
    }
    public UpdateUserService(OnUpdateUserService l, User user, String progressDialogMessage){
        this.myListener = l;
        setUser(user);
        setProgressDialog(getContext(),progressDialogMessage);
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "updateuser";
    }

    @Override
    protected Context getContext() {
        return getContextFromListener(myListener);
    }

    @Override
    protected void serviceDone(JSONObject result) {
        if(myListener == null){
            onListenerNull(this);
            return;
        }
        try{
            int success = result.getJSONObject("data").getInt("success");
            String message = result.getJSONObject("data").getString("message");
            if(success ==1){
                myListener.onUserUpdate(true,message);
            }
            else {
                myListener.onUserUpdate(false,message);
            }
        }catch (JSONException json) {
            onJSONConversionError(json);
        }
    }

    public void setUser(User user){
        setParams(user);
    }

    @Override
    protected Param[] serviceParam() {
        return params;
    }
    private void setParams(User user){
        List<Param> p = user.toParam();
        this.params = p.toArray(new Param[p.size()]);
    }
}
