package hr.videomarketing.Utils;

import hr.videomarketing.Models.BaseModel.Video;

/**
 * Created by bagy on 30.11.2016..
 */

public interface VideoTransfer {
    Video[] getVideos(String condition);
    void updateList(boolean showProgressBar);
    void saveVideoList(Video[] videoList);
    void setRefreshButtonVisibility(boolean visibility);
}
