package hr.videomarketing.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import hr.videomarketing.CustomViews.MyVideoViewIcon;
import hr.videomarketing.Models.BaseModel.Video;
import hr.videomarketing.R;
import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 30.11.2016..
 */

public class VideoAdapterOldVid extends BaseAdapter implements VideoClickListener {

    @Override
    public void onVideoSelected(Video selectedVideo) {
        log("Selected Video>" + selectedVideo.getTitle());
    }

    public static class VideoViewHolder {
        MyVideoViewIcon myVideoView;
    }

    LayoutInflater inflater;
    Context context;
    Video[] videos = null;
    VideoClickListener videoListener;
    VideoAdapterOldVid.VideoViewHolder holder;

    public VideoAdapterOldVid(Context context, Video[] data) {
        this.context = context;
        //this.videoListener = list;
        this.videos = data;
    }

    private LayoutInflater getInflater(Object context) {
        if (context instanceof Activity) return ((Activity) context).getLayoutInflater();
        else if (context instanceof Fragment)
            return ((Fragment) context).getActivity().getLayoutInflater();
        else return null;
    }

    @Override
    public int getCount() {
        return videos.length;
    }

    @Override
    public Object getItem(int i) {
        if (i < videos.length) return videos[i];
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void setVideoSelectedListener(VideoClickListener listener) {
        this.videoListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_video_list_item_view, null);

            holder = new VideoAdapterOldVid.VideoViewHolder();
            holder.myVideoView = new MyVideoViewIcon(context);
            holder.myVideoView.videoView = (ImageButton) convertView.findViewById(R.id.customVideoView);
            holder.myVideoView.tvLabel = (TextView) convertView.findViewById(R.id.twVideoLabel);
            holder.myVideoView.iconStatus = (ImageView)convertView.findViewById(R.id.myCustomVIewIcon);
            log("Video: ");
            convertView.setTag(holder);

        }
        holder = (VideoAdapterOldVid.VideoViewHolder) convertView.getTag();
        Video video = videos[position];
        if (video != null) {
            holder.myVideoView.tvLabel.setText(video.getTitle());

            //TODO:magic
        }
        return convertView;
    }

    private void log(String text) {
        VideoMarketingApp.log("VideoAdapter>" + text);
    }
}
