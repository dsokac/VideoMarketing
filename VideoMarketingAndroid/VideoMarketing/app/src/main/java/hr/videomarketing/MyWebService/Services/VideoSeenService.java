package hr.videomarketing.MyWebService.Services;
import hr.videomarketing.MyWebService.Interfaces.OnVideoInteractionService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.VideoActions;
import hr.videomarketing.MyWebService.VideoInteractionService;

import static hr.videomarketing.MyWebService.Services.VideoSeenService.URLParam.USER;
import static hr.videomarketing.MyWebService.Services.VideoSeenService.URLParam.VIDEO;

/**
 * Created by bagy on 24.11.2016..
 */

public class VideoSeenService extends VideoInteractionService {
    Param[] params;
    public VideoSeenService(OnVideoInteractionService myListener, String userId, String videoId) {
        super(myListener);
        setParams(userId,videoId);
    }
    public VideoSeenService(OnVideoInteractionService myListener,String userId,String videoId,String progressBar) {
        super(myListener);
        setParams(userId,videoId);
        setProgressDialog(getContext(),progressBar);
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "view_video";
    }

    @Override
    public VideoActions getAction() {
        return VideoActions.SEEN;
    }

    public void setParams(String userId, String videoId){
        params = new Param[]{
                new Param(VIDEO,videoId),
                new Param(USER,userId)
        };
    }
    @Override
    protected Param[] serviceParam() {
        return params;
    }

    public final static class URLParam{
        public final static String USER = "user";
        public final static String VIDEO = "video";
    }
}
