package hr.videomarketing.MyWebService.Services;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.Models.BaseModel.UserStatus;
import hr.videomarketing.MyWebService.Interfaces.OnUserStatusInteractionService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.Utils.WebServiceException;
import hr.videomarketing.MyWebService.VideoMarketingWebService;

import static hr.videomarketing.MyWebService.Services.UserStatusService.URLParam.PROVIDER;
import static hr.videomarketing.MyWebService.Services.UserStatusService.URLParam.USER;

/**
 * Created by bagy on 25.11.2016..
 */

public class UserStatusService extends VideoMarketingWebService {
    Param[] params;
    OnUserStatusInteractionService myListener;
    User user = null;

    public UserStatusService(OnUserStatusInteractionService myListener, User user, String providerCode) {
        this.myListener = myListener;
        this.user = user;
        setParams(user,providerCode);
    }
    public UserStatusService(OnUserStatusInteractionService myListener,User user,String providerCode,String progresBar) {
        this.myListener = myListener;
        this.user = user;
        setParams(user,providerCode);
        setProgressDialog(getContext(),progresBar);
    }
    public void setParams(User user, String telecomOperatorId){
        this.params = new Param[]{
                new Param(USER,Long.toString(user.getId())),
                new Param(PROVIDER,telecomOperatorId)
        };
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "current_user_points";
    }

    @Override
    protected Context getContext() {
        return getContextFromListener(myListener);
    }

    @Override
    protected Param[] serviceParam() {
        return this.params;
    }

    @Override
    protected void serviceDone(JSONObject result) {
        if(myListener == null){
            onListenerNull(this);
        }
        try{
            JSONObject data = result.getJSONObject("data");
            UserStatus status = UserStatus.newInstance(data,user);
            myListener.onUserStatusService(status);
        }catch (JSONException js){
           onJSONConversionError(js);
        }
    }

    public final static class URLParam{
        public final static String USER="user_id";
        public final static String PROVIDER = "telecomm_operator";
    }
}
