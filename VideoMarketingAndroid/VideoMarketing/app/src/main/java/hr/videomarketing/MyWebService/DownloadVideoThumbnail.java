package hr.videomarketing.MyWebService;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.MyWebService.Interfaces.OnVideoThumbnailDownloaded;
import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 3.12.2016..
 */

public class DownloadVideoThumbnail extends AsyncTask<Video,Video,Video[]>{
    private InputStream inputStream;
    private Exception error;


    @Override
    protected Video[] doInBackground(Video... lists) {
        try{
            for (int i = 0; i < lists.length; i++) {
                inputStream = new URL(lists[i].getThumbnailUrl()).openStream();
                lists[i].setThumbnail(BitmapFactory.decodeStream(inputStream));
                publishProgress(lists[i]);
                log("video:"+lists[i].getDmID()+">thumbnail downlaoded");
            }
        }catch (IOException ioe){
            log("input stream exception>"+ioe.getMessage());
            ioe.printStackTrace();

        }
        return lists;
    }

    @Override
    protected void onProgressUpdate(Video... values) {
        super.onProgressUpdate(values);
        for (int i = 0; i < values.length; i++) {
            values[i].thumbnailDownloaded();
        }
    }

    @Override
    protected void onPostExecute(Video[] video) {
        super.onPostExecute(video);
        for (int i = 0; i < video.length; i++) {
            if(video[i].getMyView()!=null)video[i].thumbnailDownloaded();
        }
    }

    private void log(String txt){
        VideoMarketingApp.log("DownloadVideoThumbnail>"+txt);
    }
}
