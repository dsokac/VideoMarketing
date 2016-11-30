package hr.videomarketing.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hr.videomarketing.DMVideoView.DMWebVideoView;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.R;
import hr.videomarketing.Utils.VideoClickListener;
import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 24.11.2016..
 */

public class MyVideoView extends RelativeLayout implements View.OnTouchListener,View.OnClickListener{
    public DMWebVideoView videoView;
    public TextView tvLabel;
    VideoClickListener myList;

    public MyVideoView(Context context) {
        super(context);
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

    protected void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_video_list_item_view,this);
        //videoView = (DMWebVideoView)findViewById(R.id.customVideoView);
        //tvLabel = (TextView)findViewById(R.id.twVideoLabel);
    }
    public DMWebVideoView getVideoView() {
        return videoView;
    }

    public TextView getTvLabel() {
        return tvLabel;
    }

    public void setViewListener(VideoClickListener listener){
        this.myList = listener;
        if(videoView !=null){
            videoView.setOnClickListener(this);
            //videoView.setOnTouchListener(this);
            tvLabel.setOnTouchListener(this);
        }
        else{
            log("dm video view is null");
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(view instanceof DMWebVideoView){
                    myList.onVideoSelected(videoView.getVideo());
                }
                else if(view instanceof TextView){
                    myList.onVideoSelected(videoView.getVideo());
                }
                break;
        }
        return false;
    }
    private void log(String text){
        VideoMarketingApp.log("MyVideoView>"+text);
    }

    @Override
    public void onClick(View view) {
        if(view instanceof DMWebVideoView){
            myList.onVideoSelected(videoView.getVideo());
        }
    }
}
