package hr.videomarketing;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import hr.videomarketing.Utils.ProviderFactory;
import hr.videomarketing.Utils.ProviderProducer;


/**
 * Created by bagy on 28.10.2016..
 */

public final class VideoMarketingApp extends Application {
    public static ProviderFactory PROVIDER;
    public final static int PERMISSION_READ_PHONE_STATE = 10001;

    public static Fragment ACTIVE_FRAGMENT=null;


    public static void setStatusBarColor(Activity activity){
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(activity.getResources().getColor(PROVIDER.getColors().getDarkToolbarColor()));
        }
    }
    public static void setNavigationBarColor(Activity activity){
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(activity.getResources().getColor(PROVIDER.getColors().getDarkToolbarColor()));
        }
    }
    public static void setOperator(String networkOperator) {
        PROVIDER = ProviderProducer.getProvider(networkOperator);
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view!=null)inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    public static void log(String text){
        Log.i("VideoMarketingApp","bagy94> "+text);
    }
    public static void replaceFragment (Fragment fragment,int container, FragmentManager fragmentManager){
        if (ACTIVE_FRAGMENT != null && fragment.getClass().getName().equals(ACTIVE_FRAGMENT.getClass().getName())){
            log("replaceFragment>reload");
        }
        else {
            String backStateName =  fragment.getClass().getName();
            String fragmentTag = backStateName;

            FragmentManager manager = fragmentManager;
            boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

            if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(container, fragment, fragmentTag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(backStateName);
                ft.commit();

            }
            ACTIVE_FRAGMENT = fragment;
            log("replaceFragment>new active>"+ACTIVE_FRAGMENT.getClass().getName());
        }
    }
    private static void reloadFragment(Fragment fragment){
        if(fragment != null){
            FragmentTransaction ft = fragment.getFragmentManager().beginTransaction();
            Bundle args = fragment.getArguments();
            ft.detach(fragment);
            fragment.setArguments(args);
            ft.attach(fragment).commit();
        }
    }
    public static void requestUnspecifiedOrientation(Activity act){
        act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
    public static void requestPortraitOrientation(Activity act){
        act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
