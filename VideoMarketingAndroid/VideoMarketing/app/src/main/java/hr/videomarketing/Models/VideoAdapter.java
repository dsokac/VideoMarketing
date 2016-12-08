package hr.videomarketing.Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hr.videomarketing.CustomViews.MyVideoView;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.R;
import hr.videomarketing.Utils.VideoClickListener;
import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 24.11.2016..
 */

public class VideoAdapter extends BaseAdapter{
    public class VideoViewHolder{
        MyVideoView myVideoView;
    }
    LayoutInflater inflater;
    Context context;
    Video[] videos = null;
    VideoClickListener videoListener;
    public VideoAdapter(Context context, Video[] data){
        this.context = context;
        //this.videoListener = list;
        this.videos = data;
    }


    @Override
    public int getCount() {
        return videos.length;
    }

    @Override
    public Object getItem(int i) {
        if(i< videos.length)return videos[i];
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public void setVideoSelectedListener(VideoClickListener listener){
        this.videoListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final VideoViewHolder viewHolder;
        final Video video = videos[position];
        log("VideoAdapter>VideoName> "+video.getTitle());
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_video_list_item_view,null);

            viewHolder = new VideoViewHolder();
            viewHolder.myVideoView = new MyVideoView(context);
            viewHolder.myVideoView.setVideoView((ImageButton) convertView.findViewById(R.id.customVideoView));
            viewHolder.myVideoView.setTvLabel((TextView)convertView.findViewById(R.id.twVideoLabel));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            viewHolder.myVideoView.setLayoutParams(params);

            log("Video: ");
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (VideoViewHolder)convertView.getTag();
        }
        if(video != null){
            viewHolder.myVideoView.setVideo(video);
            viewHolder.myVideoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(videoListener != null){
                        videoListener.onVideoSelected(viewHolder.myVideoView.getVideo());
                    }
                }
            });
            //TODO:magic
        }
        return convertView;
    }
    private void log(String text){
        VideoMarketingApp.log("VideoAdapter>"+text);
    }
}
