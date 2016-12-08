package hr.videomarketing.MyWebService.Services;

import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.MyWebService.Interfaces.OnUpdateUserProviderService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.VideoMarketingWebService;

/**
 * Created by bagy on 24.11.2016..
 */

public class UpdateUserProvider extends VideoMarketingWebService {
    private final static String PARAM_USER = "id";
    private final static String PARAM_PROVIDER = "telecomm";
    OnUpdateUserProviderService myListener;
    Param[] params;
    private String progresDialogMessage;

    public UpdateUserProvider(OnUpdateUserProviderService myListener, String userId, String providerCode) {
        super(myListener);
        this.myListener = myListener;
        setParam(userId,providerCode);
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "change_telecomm";
    }

    @Override
    protected void serviceDone(JSONObject result) {
        if(myListener == null){
            onListenerNull(this);
            return;
        }
        try {
            int success = result.getJSONObject("data").getInt("success");
            if(success == 1 ){
                myListener.onUserProviderUpdated(true);
            }
            else {
                myListener.onUserProviderUpdated(false);
            }
        }catch (JSONException js){
            onJSONConversionError(js);
        }
    }

    @Override
    protected Param[] serviceParam() {
        return params;
    }
    private void setParam(String userId,String providerCode){
        this.params= new Param[]{
                new Param(PARAM_USER,userId),
                new Param(PARAM_PROVIDER,providerCode)
        };
    }
}
