package hr.videomarketing.MyWebService.Services;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.MyWebService.Interfaces.VideoListInteractionService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.Utils.WebServiceException;
import hr.videomarketing.MyWebService.VideoMarketingWebService;

import static hr.videomarketing.MyWebService.Services.VideoListService.UrlParam.USER_ID;

/**
 * Created by bagy on 23.11.2016..
 */

public class VideoListService extends VideoMarketingWebService {
    protected final static class UrlParam{
        final static String USER_ID = "id";
    }

    private Param userId;
    private VideoListInteractionService myListener;

    public VideoListService(long userIdParam, VideoListInteractionService listServiceInteracion){
        userId = new Param(USER_ID,Long.toString(userIdParam));
        myListener = listServiceInteracion;
    }
    public VideoListService(long userIdParam, VideoListInteractionService listServiceInteracion, String progresDialogMessage){
        userId = new Param(USER_ID,Long.toString(userIdParam));
        myListener = listServiceInteracion;
        setProgressDialog(getContextFromListener(listServiceInteracion),progresDialogMessage);
    }
    public Param getUserId() {
        return userId;
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "videos";
    }

    @Override
    protected void serviceDone(JSONObject result) {
        if(myListener == null){
            onListenerNull(this);
            return;
        }
        try {
            JSONArray jsonArray = result.getJSONArray("data");
            List<Video> videoList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                videoList.add(Video.newInstance(jsonArray.getJSONObject(i)));
            }
            myListener.onVideosReady(videoList);
        }catch (JSONException jsonE){
            onJSONConversionError(jsonE);
        }
    }

    @Override
    protected Context getContext() {
        return getContextFromListener(myListener);
    }

    @Override
    public Param[] serviceParam() {
        return new Param[]{getUserId()};
    }
}
