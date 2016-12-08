package hr.videomarketing.MyWebService.Services;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.Models.BaseModel.UserStatus;
import hr.videomarketing.MyWebService.Interfaces.OnUserStatusInteractionService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.VideoMarketingWebService;
import static hr.videomarketing.MyWebService.Services.UserStatusService.URLParam.USER;

/**
 * Created by bagy on 25.11.2016..
 */

public class UserStatusService extends VideoMarketingWebService {
    Param[] params;
    OnUserStatusInteractionService myListener;
    User user = null;

    public UserStatusService(OnUserStatusInteractionService myListener,User user) {
        super(myListener);
        this.myListener = myListener;
        this.user = user;
        setParams(user);
    }
    public void setParams(User user){
        this.params = new Param[]{
                new Param(USER,Long.toString(user.getId()))
        };
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "points_per_month";
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
            JSONArray data = result.getJSONArray("data");
            user.setStatus(data);
            myListener.onUserStatusService(user);
        }catch (JSONException js){
           onJSONConversionError(js);
            Toast.makeText(getContext(),"Nema podataka za trazenog korisnika",Toast.LENGTH_SHORT).show();
        }
    }

    public final static class URLParam{
        public final static String USER="user";
    }
}
