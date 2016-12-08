package hr.videomarketing.Utils;
import android.util.Patterns;


import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 4.12.2016..
 */
public class UserDataCheck{

    private static UserDataCheck instance = new UserDataCheck();

    public static UserDataCheck getInstance(){return instance;}

    private UserDataCheck() {
    }
    public boolean checkName(String name){
        String[] userNameSurname = getNameSurnameArray(name);
        return  userNameSurname.length >=2?true:false;
    }
    public boolean checkUserName(String userName){
        return userName.length() <= 3?false:true;
    }
    public boolean checkBirthday(String brth){
        if(brth.length()<8)return false;
        return true;
    }
    public boolean checkEmail(String email){
        return email.length()>7?Patterns.EMAIL_ADDRESS.matcher(email).matches():false;
    }
    public boolean checkPassword(String password){
        return password.length()<6?false:true;
    }
    public boolean checkPhoneNumber(String number){
        if(number.length() < 10) return false;
        String pattern = "+385";
        for (int i = 0; i < pattern.length(); i++) {
            if(pattern.charAt(i) != number.charAt(i)){
                return false;
            }
        }
        return true;
    }



    private String[] getNameSurnameArray(String input){
        String[] data = input.split(" ");
        if(data.length>0) {
            return data;
        }
        return null;
    }

    private void log(String s) {
        VideoMarketingApp.log("class>userdatacheck>check>"+s);
    }
}
