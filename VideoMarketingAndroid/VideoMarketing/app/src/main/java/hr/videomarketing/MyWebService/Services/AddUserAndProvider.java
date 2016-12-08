package hr.videomarketing.MyWebService.Services;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.MyWebService.Interfaces.OnUpdateUserProviderService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.Utils.WebServiceException;
import hr.videomarketing.MyWebService.VideoMarketingWebService;

import static hr.videomarketing.MyWebService.Services.AddUserAndProvider.UrlParam.PARAM_PROVIDER;
import static hr.videomarketing.MyWebService.Services.AddUserAndProvider.UrlParam.PARAM_USER;

/**
 * Created by bagy on 24.11.2016..
 */

public class AddUserAndProvider extends VideoMarketingWebService {
    public static class UrlParam{
        public final static String PARAM_USER = "user";
        public final static String PARAM_PROVIDER = "telecomm";
    }
    private Param[] params;
    private String progresDialogMessage;
    private OnUpdateUserProviderService myListener;


    public AddUserAndProvider(OnUpdateUserProviderService myListener,String userID,String providerCode) {
        super(myListener);
        this.myListener = myListener;
        this.params = new Param[]{
                new Param(PARAM_USER,userID),
                new Param(PARAM_PROVIDER,providerCode)
        };
    }

    public AddUserAndProvider(String userID,String providerCode, String progresDialogMessage, OnUpdateUserProviderService myListener) {
        super(myListener);
        this.progresDialogMessage = progresDialogMessage;
        this.myListener = myListener;
        this.params = new Param[]{
                new Param(PARAM_USER,userID),
                new Param(PARAM_PROVIDER,providerCode)
        };
        setProgressDialog(progresDialogMessage);
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "add_telecomm_user";
    }

    @Override
    protected void serviceDone(JSONObject result) {
        try {
            int success = result.getJSONObject("data").getInt("success");
            if(myListener == null){
                onListenerNull(this);
                return;
            }
            if(success == 1){
                myListener.onUserProviderUpdated(true);
            }
            else{
                myListener.onUserProviderUpdated(false);
            }
        }catch (JSONException jsonE){
            onJSONConversionError(jsonE);
        }
    }

    @Override
    public Param[] serviceParam() {
        return params;
    }

}
