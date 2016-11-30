package hr.videomarketing.MyWebService.Services;

import hr.videomarketing.MyWebService.Interfaces.OnVideoInteractionService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.VideoActions;
import hr.videomarketing.MyWebService.VideoInteractionService;

import static hr.videomarketing.MyWebService.Services.LikeVideoService.URLParam.LIKE;
import static hr.videomarketing.MyWebService.Services.LikeVideoService.URLParam.USER;
import static hr.videomarketing.MyWebService.Services.LikeVideoService.URLParam.VIDEO;

/**
 * Created by bagy on 24.11.2016..
 */

public class LikeVideoService extends VideoInteractionService {

    Param[] params=null;

    public LikeVideoService(OnVideoInteractionService myListener, String userId, String videoId, String likeStatus) {
        super(myListener);
        setLike(userId,videoId,likeStatus);
    }
    public LikeVideoService(OnVideoInteractionService myListener, String userId, String videoId, String likeStatus,String progressDialogMessage) {
        super(myListener);
        setLike(userId,videoId,likeStatus);
        setProgressDialog(getContext(),progressDialogMessage);
    }

    @Override
    public VideoActions getAction() {
        return VideoActions.LIKE;
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "videolike";
    }

    @Override
    protected Param[] serviceParam() {
        return params;
    }

    public void setLike(String userId, String videoId, String likeStatus){
        this.params = new Param[]{
                new Param(USER,userId),
                new Param(VIDEO,videoId),
                new Param(LIKE,likeStatus)
        };
    }
    public final static class URLParam{
        public final static String USER = "user";
        public final static String VIDEO = "video";
        public final static String LIKE = "like";
    }
}
