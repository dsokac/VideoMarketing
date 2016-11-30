package hr.videomarketing.MyWebService;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.MyWebService.Interfaces.OnVideoInteractionService;
import hr.videomarketing.MyWebService.Utils.WebServiceException;

/**
 * Created by bagy on 24.11.2016..
 */

public abstract class VideoInteractionService extends VideoMarketingWebService {
    OnVideoInteractionService myListener;

    public VideoInteractionService(OnVideoInteractionService myListener){
        this.myListener = myListener;
    }

    @Override
    protected Context getContext() {
        return getContextFromListener(myListener);
    }

    @Override
    protected void serviceDone(JSONObject result) {
        try {
            JSONObject jsonObject = result.getJSONObject("data");
            int success = jsonObject.getInt("success");
            String message = jsonObject.getString("message");
            myListener.onVideoInteractionService(getAction(),success,message);
        }catch (JSONException jsonE){
            throw new WebServiceException(WebServiceException.CONVERT_TO_JSON_EXCEPTION,jsonE);
        }
    }
    public abstract VideoActions getAction();
}
