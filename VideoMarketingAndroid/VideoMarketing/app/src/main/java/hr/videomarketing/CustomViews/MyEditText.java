package hr.videomarketing.CustomViews;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import android.widget.EditText;

import hr.videomarketing.VideoMarketingApp;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;

/**
 * Created by bagy on 24.11.2016..
 */

public class MyEditText extends EditText {


    public MyEditText(Context context) {
        super(context);
        setTintView(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTintView(context);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTintView(context);
    }

    public void setTintView(Context context) {
        if(!isInEditMode()){
            if(getContext() != null){
                Drawable nd = this.getBackground().getConstantState().newDrawable();
                nd.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(
                        context.getResources().getColor(PROVIDER.getColors().getLinesColor()), PorterDuff.Mode.SRC_IN));
                this.setBackground(nd);
            }

        }
    }
}
