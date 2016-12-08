package hr.videomarketing.MyWebService.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.Models.BaseModel.VideoComment;
import hr.videomarketing.MyWebService.Interfaces.OnRetrieveVideoCommentsService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.VideoMarketingWebService;

import static hr.videomarketing.MyWebService.Services.VideoCommentsService.URLParam.VIDEO;

/**
 * Created by bagy on 24.11.2016..
 */

public class VideoCommentsService extends VideoMarketingWebService {
    Param[] params;
    OnRetrieveVideoCommentsService myListener;
    public VideoCommentsService(OnRetrieveVideoCommentsService myListener, String videoId){
        super(myListener);
        this.myListener = myListener;
        setParams(videoId);
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "video_comments";
    }

    @Override
    protected Param[] serviceParam() {
        return params;
    }

    @Override
    protected void serviceDone(JSONObject result) {
        if(myListener == null){
            onListenerNull(this);
            return;
        }
        try {
            JSONArray jsonArray = result.getJSONArray("data");
            VideoComment[] comments = new VideoComment[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                comments[i] = VideoComment.newInstance(jsonArray.getJSONObject(i));
            }
            myListener.onVideoCommentsRetrieved(comments);
        }catch (JSONException jsonE){
            onJSONConversionError(jsonE);
        }
    }

    public void setParams(String videoId){
        params = new Param[]{
                new Param(VIDEO,videoId)
        };
    }

    public final static class URLParam{
        public final static String VIDEO = "video";
    }
}
