package hr.videomarketing.Utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bagy on 5.12.2016..
 */

public final class DialogStateAdapter {
    private final static String inFileLabel = "hr.videomarketing.showDialogInPlayVideoFragment";
    public static boolean saveStatus(Context context, boolean status){
        return Files.DIALOG_SHOW_STATE.write(context,inFileData(status));
    }
    private static String inFileData(boolean status){
        return "{\""+inFileLabel+"\":\""+Boolean.toString(status)+"\"}";
    }
    public static boolean isShowing(Context context){
        try{
            Object data = Files.DIALOG_SHOW_STATE.read(context);
            if(data != null){
                String dataString = (String)data;
                JSONObject jsonObject = new JSONObject(dataString);
                return jsonObject.getBoolean(inFileLabel);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return true;
    }
}
