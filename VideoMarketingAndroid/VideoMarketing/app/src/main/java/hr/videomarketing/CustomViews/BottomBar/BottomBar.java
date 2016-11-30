package hr.videomarketing.CustomViews.BottomBar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import hr.videomarketing.CustomViews.BottomBar.Utils.BottomBarItem;
import hr.videomarketing.R;
import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 20.11.2016..
 */

public class BottomBar extends RelativeLayout{

    VideoBBI videoBBI = null;
    PointsBBI pointsBBI = null;
    LikesBBI likesBBI = null;
    SeenBBI seenBBI = null;
    public BottomBar(Context context) {
        super(context);
        init(context,null);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public BottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        /*TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomBar,0,0);
        int color = a.getColor(R.styleable.BottomBar_backgroundColor,getResources().getColor(R.color.colorPrimaryDark));
        setBackgroundColor(color);*/
        LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inf.inflate(R.layout.bottom_bar_layout,this,true);




        videoBBI = (VideoBBI) findViewById(R.id.videoBBItem);
        pointsBBI = (PointsBBI)findViewById(R.id.pointsBBItem);
        likesBBI = (LikesBBI) findViewById(R.id.likesBBItem);
        seenBBI = (SeenBBI) findViewById(R.id.seenBBItem);

        videoBBI.setWidth(BottomBarItem.convertDpToPixel(65,getContext()));
    }

    /*public void changeItemIcon(BottomBarItem item){
        BottomBarItem bbi;
        switch (item){
            case videoBBI:
                bbi = (BottomBarItem)findViewById(R.id.videoBBItem);
                bbi.setItemIcon(imgRes);
                break;
            case Points:
                bbi = (BottomBarItem)findViewById(R.id.pointsBBItem);
                bbi.setItemIcon(imgRes);
                break;
            case Likes:
                bbi = (BottomBarItem)findViewById(R.id.likesBBItem);
                bbi.setItemIcon(imgRes);
                break;
            case Seen:
                bbi = (BottomBarItem)findViewById(R.id.seenBBItem);
                bbi.setItemIcon(imgRes);
                break;
            default:

        }
    }
    public void changeItemLabel(Items item, String label){
        BottomBarItem bbi;
        switch (item){
            case Video:
                bbi = (BottomBarItem)findViewById(R.id.videoBBItem);
                bbi.setItemText(label);
                break;
            case Points:
                bbi = (BottomBarItem)findViewById(R.id.pointsBBItem);
                bbi.setItemText(label);
                break;
            case Likes:
                bbi = (BottomBarItem)findViewById(R.id.likesBBItem);
                bbi.setItemText(label);
                break;
            case Seen:
                bbi = (BottomBarItem)findViewById(R.id.seenBBItem);
                bbi.setItemText(label);
                break;
            default:

        }
    }*/
    public void setOnItemsClickListener(OnClickListener listener){
        videoBBI.setOnClickListener(listener);
        pointsBBI.setOnClickListener(listener);
        likesBBI.setOnClickListener(listener);
        seenBBI.setOnClickListener(listener);
    }
    private void log(String text){
        VideoMarketingApp.log("BottomBar>"+text);
    }
}
