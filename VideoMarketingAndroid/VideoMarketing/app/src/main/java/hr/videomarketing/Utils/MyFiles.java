package hr.videomarketing.Utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.videomarketing.Models.BaseModel.User;

/**
 * Created by bagy on 6.11.2016..
 */

public final class MyFiles {
    public enum Files {
        USER_DATA_FILE("account_data"),
        USER_STATE("user_loged_in");
        private String path;

        Files(String path) {
            this.path = path;
        }

        protected String getPath() {
            return path;
        }
    }

    public static boolean writeInFile(Context context, Files file, String contentToWrite) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(file.getPath(), Context.MODE_PRIVATE);
            String stringToWrite = contentToWrite.replace(" ","");
            fos.write(stringToWrite.getBytes());
            Log.i("VMFile: ","write: "+stringToWrite);
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    public static Object readFromFIle(Context context, Files file) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(file.getPath());
            switch (file) {
                case USER_DATA_FILE:
                    return User.newInstance(readData(fis));
                case USER_STATE:
                    return readData(fis);
                default:
                    Log.w("VMApp: ", "error_no_file_found");
                    return null;
            }
        } catch (IOException fnfE) {
            fnfE.printStackTrace();
        }
        return null;
    }
    private static String readData(FileInputStream fis) throws IOException{
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis));
        String line = "";
        StringBuffer strbuffer = new StringBuffer();
        while ((line = bufferReader.readLine()) != null) {
            strbuffer.append(line);
            Log.w("VMApp: ", "file_read: "+line);
        }
        bufferReader.close();
        fis.close();
        Log.w("VMApp: ", strbuffer.toString());
        return strbuffer.toString();
    }
}
