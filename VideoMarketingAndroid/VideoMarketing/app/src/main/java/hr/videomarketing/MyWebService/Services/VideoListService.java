package hr.videomarketing.MyWebService.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.MyWebService.DownloadVideoThumbnail;
import hr.videomarketing.MyWebService.Interfaces.VideoListInteractionService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.VideoMarketingWebService;

import static hr.videomarketing.MyWebService.Services.VideoListService.UrlParam.USER_ID;

/**
 * Created by bagy on 23.11.2016..
 */

public class VideoListService extends VideoMarketingWebService {
    protected final static class UrlParam{
        final static String USER_ID = "id";
    }

    private Param[] param;
    private VideoListInteractionService myListener;

    public VideoListService(long userIdParam, VideoListInteractionService listServiceInteracion){
        super(listServiceInteracion);
        setParam(Long.toString(userIdParam));
        myListener = listServiceInteracion;
        setProgressDialog("Dohvaćanje videa",false);
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
            Video[] videoList = new Video[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                videoList[i]=Video.newInstance(jsonArray.getJSONObject(i));
            }

            new DownloadVideoThumbnail().execute(videoList);

            closeProgresssDialog();
            myListener.onVideosReady(videoList);
        }catch (JSONException jsonE){
            onJSONConversionError(jsonE);
        }
    }
    public void setParam(String userId){
        this.param = new Param[]{
                new Param(USER_ID,userId)
        };
    }

    @Override
    public Param[] serviceParam() {
        return this.param;
    }
}
