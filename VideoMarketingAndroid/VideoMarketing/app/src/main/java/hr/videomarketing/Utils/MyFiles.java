package hr.videomarketing.Utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.videomarketing.Models.BaseModel.User;
import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 6.11.2016..
 */

public class MyFiles {
    private static MyFiles instance;
    public enum Files {
        USER_DATA_FILE("account_data"),
        USER_PROVIDER("user_provider"),
        DIALOG_SHOW_STATE("play_video_dialog_show_state");
        private String path;

        Files(String path) {
            this.path = path;
        }

        protected String getPath() {
            return path;
        }
    }
    private MyFiles(){

    }

    public static MyFiles getInstance(){
        if(instance == null){
            instance = new MyFiles();
        }
        return instance;
    }

    public boolean writeInFile(Context context, Files file, String contentToWrite) {
        if(context == null|| file == null || contentToWrite == null)return false;
        try {
            FileOutputStream fos = context.openFileOutput(file.getPath(), Context.MODE_PRIVATE);
            String stringToWrite = contentToWrite.replace(" ","");
            fos.write(stringToWrite.getBytes());
            log("myFiles>write");
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    public Object readFromFIle(Context context, Files file) {
        if(context == null|| file == null)return null;
        try {
            FileInputStream fis = context.openFileInput(file.getPath());
            switch (file) {
                case USER_DATA_FILE:
                    return User.newInstance(readData(fis));
                case USER_PROVIDER:
                    return readData(fis);
                case DIALOG_SHOW_STATE:
                    return readData(fis);
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
        VideoMarketingApp.log("MyFile>"+txt);
    }
}
