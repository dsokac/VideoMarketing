package hr.videomarketing.MyWebService.Interfaces;
import hr.videomarketing.Models.BaseModel.VideoComment;
import hr.videomarketing.MyWebService.Utils.WebServiceException;

/**
 * Created by bagy on 24.11.2016..
 */

public interface OnRetrieveVideoCommentsService {
    void onVideoCommentsRetrieved(VideoComment[] comments);
}
