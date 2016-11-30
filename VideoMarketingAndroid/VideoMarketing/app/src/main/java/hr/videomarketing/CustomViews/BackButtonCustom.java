package hr.videomarketing.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hr.videomarketing.R;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;

/**
 * Created by bagy on 25.11.2016..
 */

public class BackButtonCustom extends RelativeLayout {
    ImageView iwIcon;
    TextView twLabel;
    public BackButtonCustom(Context context) {
        super(context);
        if(!isInEditMode())init();
    }

    public BackButtonCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())init();
    }

    public BackButtonCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode())init();
    }

    private void init(){
        LayoutInflater inf = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inf.inflate(R.layout.layout_back_button,this);
        setBackgroundColor(getResources().getColor(PROVIDER.getButtonColors()));
    }
}
