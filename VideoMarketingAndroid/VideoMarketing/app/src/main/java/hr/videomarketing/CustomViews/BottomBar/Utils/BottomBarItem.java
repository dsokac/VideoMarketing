package hr.videomarketing.CustomViews.BottomBar.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hr.videomarketing.R;
import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 20.11.2016..
 */

public abstract class BottomBarItem extends RelativeLayout{

    private ImageView iwIcon;
    private TextView tvLabel;
    public BottomBarItem(Context context) {
        super(context);
    }

    public BottomBarItem(Context context, AttributeSet attrs) throws BottomBarException {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomBarItem,0,0);
        String label = array.getString(R.styleable.BottomBarItem_textLabel);
        int image = array.getResourceId(R.styleable.BottomBarItem_icon,0);
        if(image == 0)throw new BottomBarException(BottomBarException.resourceNotFoundException);
        if(getId() == -1)throw new BottomBarException(BottomBarException.actionIdIsNull);
        array.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_layout,this);

        iwIcon = (ImageView)findViewById(R.id.iconView);
        tvLabel = (TextView)findViewById(R.id.label);

        iwIcon.setImageResource(image);
        tvLabel.setText(label);
        try {
            setOnClickListener((OnClickListener)getParent());
        }catch (ClassCastException exc){
            exc.printStackTrace();
        }
    }


    public void setItemText(String text){
        if(tvLabel != null){
            tvLabel.setText(text);
        }
    }
    public void setItemIcon(int iconResID){
        if(iwIcon !=null){
            iwIcon.setImageResource(iconResID);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BottomBarItem){
            BottomBarItem item = (BottomBarItem) obj;
            if(item.getId() == this.getId()){
                return true;
            }
        }
        return false;
    }
    private void dpToPx(int dp){

    }


    private void log(String text){
        VideoMarketingApp.log("BottomBar>Item>"+text);
    }
    public void setWidth(int widthDP){
        getLayoutParams().width = widthDP;
    }
    public void setHeight(int heightDp){
        getLayoutParams().height = heightDp;
    }
    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)px;
    }
    public static int convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)dp;
    }
}
