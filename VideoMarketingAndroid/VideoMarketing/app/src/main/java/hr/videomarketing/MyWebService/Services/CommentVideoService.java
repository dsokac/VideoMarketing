package hr.videomarketing.MyWebService.Services;

import hr.videomarketing.MyWebService.Interfaces.OnVideoInteractionService;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.VideoActions;
import hr.videomarketing.MyWebService.VideoInteractionService;

import static hr.videomarketing.MyWebService.Services.CommentVideoService.URLParam.COMMENT;
import static hr.videomarketing.MyWebService.Services.CommentVideoService.URLParam.USER;
import static hr.videomarketing.MyWebService.Services.CommentVideoService.URLParam.VIDEO;

/**
 * Created by bagy on 24.11.2016..
 */

public class CommentVideoService extends VideoInteractionService {

    Param[] params;

    public CommentVideoService(OnVideoInteractionService myListener, String videoId, String authorId, String content) {
        super(myListener);
        setParams(videoId,authorId,content);
    }

    public void setParams(String videoId, String authorId, String content){
        String con = content.replace(" ","_");
        this.params = new Param[]{
                new Param(VIDEO,videoId),
                new Param(USER,authorId),
                new Param(COMMENT,con)
        };
    }

    @Override
    public VideoActions getAction() {
        return VideoActions.COMMENT;
    }

    @Override
    public String getVideoMarketingServicePath() {
        return "comment_video";
    }

    @Override
    protected Param[] serviceParam() {
        return params;
    }

    public final static class URLParam{
        public final static String USER = "author";
        public final static String VIDEO = "video";
        public final static String COMMENT = "content";
    }
}
