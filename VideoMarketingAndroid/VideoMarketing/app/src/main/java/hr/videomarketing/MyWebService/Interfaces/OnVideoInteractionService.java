package hr.videomarketing.MyWebService.Interfaces;
import hr.videomarketing.MyWebService.VideoActions;

/**
 * Created by bagy on 24.11.2016..
 */

public interface OnVideoInteractionService {
    void onVideoInteractionService(VideoActions action, int succes, String message);
}
