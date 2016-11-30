package hr.videomarketing.MyWebService.Interfaces;

import java.util.List;

import hr.videomarketing.Models.BaseModel.Video;

/**
 * Created by bagy on 25.11.2016..
 */
public interface VideoListInteractionService {
    void onVideosReady(List<Video> videoList);
}
