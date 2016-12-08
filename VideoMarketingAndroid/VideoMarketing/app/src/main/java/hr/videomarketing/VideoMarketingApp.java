package hr.videomarketing;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import hr.videomarketing.Utils.ProviderFactory;
import hr.videomarketing.Utils.ProviderProducer;


/**
 * Created by bagy on 28.10.2016..
 */

public final class VideoMarketingApp extends Application {
    public static ProviderFactory PROVIDER;
    public final static int PERMISSION_READ_PHONE_STATE = 10001;
    public static AppCompatActivity HomeActivity = null;
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
        log("Provider set>"+PROVIDER.toString());
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
            String backStateName =  fragment.getClass().getName();
            String fragmentTag = backStateName;
            ACTIVE_FRAGMENT = fragment;
            FragmentManager manager = fragmentManager;
            boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

            if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(container, fragment, fragmentTag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(backStateName);
                ft.commit();

            }

            log("replaceFragment>new active>"+ACTIVE_FRAGMENT.getClass().getName());
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

    @Override
    public void onCreate() {
        super.onCreate();
        log("Application>OnCreate");
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //System.gc();
        log("MemoryLow>call gc");
    }

    public static void requestUnspecifiedOrientation(Activity act){
        act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
    public static void requestPortraitOrientation(Activity act){
        act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    public static void replaceFragmentWithoutBackStack(Fragment frag, int container, FragmentManager fragmanager){
        FragmentTransaction ft = fragmanager.beginTransaction();
        ft.replace(container,frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();
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
