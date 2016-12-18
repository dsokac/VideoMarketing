package hr.videomarketing.Utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.videomarketing.Models.BaseModel.GeographicUnits;
import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 18.12.2016..
 */

public enum Files{
    USER_DATA_FILE("account_data"),
    USER_PROVIDER("user_provider"),
    DIALOG_SHOW_STATE("play_video_dialog_show_state"),
    GEOLOCATIONS("geo_locations_units");
    private String path;

    Files(String path) {
        this.path = path;
    }

    protected String getPath() {
        return path;
    }

    public boolean write(Context context, String contentToWrite) {
        if(context == null|| contentToWrite == null)return false;
        try {
            FileOutputStream fos = context.openFileOutput(this.getPath(), Context.MODE_PRIVATE);
            String stringToWrite = contentToWrite.replace(" ","");
            fos.write(stringToWrite.getBytes());
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }
    public Object read(Context context){
        if(context == null)return null;
        try {
            FileInputStream fis = context.openFileInput(getPath());
            switch (this) {
                case USER_DATA_FILE:
                    return User.newInstance(readData(fis));
                case USER_PROVIDER:
                    return readData(fis);
                case DIALOG_SHOW_STATE:
                    return readData(fis);
                case GEOLOCATIONS:
                    return GeographicUnits.getListFromJsonArray(readData(fis));
                default:
                    return null;
            }
        } catch (IOException fnfE) {
            fnfE.printStackTrace();
        }
        return null;
    }
    private String readData(FileInputStream fis) throws IOException{
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis));
        String line = "";
        StringBuffer strbuffer = new StringBuffer();
        while ((line = bufferReader.readLine()) != null) {
            strbuffer.append(line);
        }
        bufferReader.close();
        fis.close();
        String data = strbuffer.toString();
        log("myFiles>read");
        return data;
    }
    private void log(String txt){
        VideoMarketingApp.log("Files>"+txt);
    }
}
