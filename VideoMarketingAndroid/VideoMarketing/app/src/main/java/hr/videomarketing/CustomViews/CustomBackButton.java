package hr.videomarketing.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hr.videomarketing.R;

/**
 * Created by bagy on 24.11.2016..
 */

public class CustomBackButton extends RelativeLayout {
    ImageView icon;
    TextView textView;
    public CustomBackButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        if(!isInEditMode())init();
    }

    private void init() {
        LayoutInflater inf = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inf.inflate(R.layout.layout_back_button,null);

        textView = (TextView) findViewById(R.id.twLabel);
        icon = (ImageView) findViewById(R.id.myCustomVIewIcon);
    }

    public void setText(String text){
        textView.setText(text);
    }
    public void setIcon(int imageResId){
        icon.setImageResource(imageResId);
    }
}
