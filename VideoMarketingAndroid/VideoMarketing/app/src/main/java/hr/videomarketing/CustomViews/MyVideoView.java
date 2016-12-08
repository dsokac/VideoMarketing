package hr.videomarketing.CustomViews;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.MyWebService.Interfaces.OnVideoThumbnailDownloaded;
import hr.videomarketing.R;
import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 24.11.2016..
 */

public class MyVideoView extends RelativeLayout implements OnVideoThumbnailDownloaded {
    public ImageButton videoView;
    public TextView tvLabel;
    Video video;

    public MyVideoView(Context context) {
        super(context);
        if(!isInEditMode())init();
    }
    public MyVideoView(Context context, Video video){
        super(context);
        setVideo(video);
        if(!isInEditMode())init();
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode())init();
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())init();
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
        if(this.tvLabel != null){
            this.tvLabel.setText(video.getTitle());
        }
        if(!video.isNullObject()){
            if(video.getThumbnail()==null)video.setMyViewListener(this);
            else{
                if(videoView != null)this.videoView.setBackground(new BitmapDrawable(getResources(),this.video.getThumbnail()));
            }
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if(videoView != null)videoView.setOnClickListener(l);
        if(tvLabel != null)tvLabel.setOnClickListener(l);
        super.setOnClickListener(l);
    }

    protected void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_video_list_item_view,this);
        //videoView = (DMWebVideoView)findViewById(R.id.customVideoView);
        //tvLabel = (TextView)findViewById(R.id.twVideoLabel);
    }
    public ImageView getVideoView() {
        return videoView;
    }

    public TextView getTvLabel() {
        return tvLabel;
    }
    public void setVideoView(ImageButton videoView) {
        this.videoView = videoView;
    }

    public void setTvLabel(TextView tvLabel) {
        this.tvLabel = tvLabel;
    }

    private void log(String text){
        VideoMarketingApp.log("MyVideoView>"+text);
    }

    @Override
    public void onImageDownloadedSuccessful() {
        if(videoView != null)this.videoView.setBackground(new BitmapDrawable(getResources(),this.video.getThumbnail()));
    }
}
