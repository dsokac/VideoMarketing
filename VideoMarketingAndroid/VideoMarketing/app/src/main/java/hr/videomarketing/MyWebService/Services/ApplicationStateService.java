package hr.videomarketing.MyWebService.Services;

import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.VideoMarketingWebService;

/**
 * Created by bagy on 02.07.17..
 */

public class ApplicationStateService extends VideoMarketingWebService {
    public interface ApplicationStateListener{
        void onApplicationStateReceived(int state);
    }

    ApplicationStateListener myListener;
    public ApplicationStateService(ApplicationStateListener obj) {
        super(obj);
        myListener = obj;
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "maintenance_notice";
    }

    @Override
    protected void serviceDone(JSONObject result) {
        if(result != null){
            try {
                myListener.onApplicationStateReceived(result.getInt("maintenance"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected Param[] serviceParam() {
        return null;
    }
}
