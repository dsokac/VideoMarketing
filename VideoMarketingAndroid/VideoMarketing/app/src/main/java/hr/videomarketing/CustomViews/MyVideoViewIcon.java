package hr.videomarketing.CustomViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import hr.videomarketing.R;

/**
 * Created by bagy on 30.11.2016..
 */

public class MyVideoViewIcon extends MyVideoView {
    public ImageView iconStatus;
    public MyVideoViewIcon(Context context) {
        super(context);
        if(!isInEditMode())initialize();
    }
    private void initialize(){
        LayoutInflater inf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inf.inflate(R.layout.layout_video_list_item_iccon_view,null);


    }

    public ImageView getIcon() {
        return iconStatus;
    }

    public void setIcon(int icon) {
        this.iconStatus.setImageResource(icon);
    }
    public void setIcon(ImageView icon) {
        this.iconStatus=icon;
    }
}
