package hr.videomarketing.Models.BaseModel;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hr.videomarketing.Utils.MyFiles;
import hr.videomarketing.MyWebService.Utils.Param;

/**
 * Created by bagy on 31.10.2016..
 */

public class User {
    private long id = 0;
    private String name=null;
    private String surname=null;
    private String birthday=null;
    private String gender=null;
    private String username=null;
    private String password=null;
    private String email=null;
    private String mobile_number=null;
    private GeographicUnits geographicUnits=null;
    private boolean state;
    public String getPhoneNumber() {
        return mobile_number;
    }

    public static User newInstance(String jsonUser){
        if(jsonUser == null||jsonUser.equals("")){
            return null;
        }
        try{
            JSONObject jsonObject = new JSONObject(jsonUser);
            User user = new User();
            user.setId(jsonObject.getInt("id"));
            user.setName(jsonObject.getString("name"));
            user.setSurname(jsonObject.getString("surname"));
            user.setBirthday(jsonObject.getString("birthday"));
            user.setGender(jsonObject.getString("gender"));
            user.setUsername(jsonObject.getString("username"));
            user.setPassword(jsonObject.getString("password"));
            user.setEmail(jsonObject.getString("email"));
            user.setPhoneNumber(jsonObject.getString("mobile_number"));
            user.setGeographicUnits(new GeographicUnits(jsonObject.getJSONObject("geographic_unit").getInt("id"),jsonObject.getJSONObject("geographic_unit").getString("name")));
            if(user.isCorrect()){
                Log.i("videomark/User: ","newInstance: "+user.toString());
                return user;
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        if(this.username != null){
            return '{' +
                    "\"id\":"+ id +
                    ",\"name\":\"" + name + '\"' +
                    ",\"surname\":\"" + surname + '\"' +
                    ",\"birthday\":\"" + birthday + '\"' +
                    ",\"gender\":\"" + gender + '\"' +
                    ",\"username\":\"" + username + '\"' +
                    ",\"password\":\"" + password + '\"' +
                    ",\"email\":\"" + email + '\"' +
                    ",\"mobile_number\":\"" + mobile_number + '\"' +
                    ",\"geographic_unit\":{"+
                    "\"id\":"+ geographicUnits.getId()+
                    ",\"name\":\""+geographicUnits.getName()+"\"}}";
        }
        return null;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mobile_number = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isState() {
        return state;
    }

    public String getMobileNumber() {
        return mobile_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GeographicUnits getGeographicUnits() {
        return geographicUnits;
    }

    public void setGeographicUnits(GeographicUnits geographicUnits) {
        this.geographicUnits = geographicUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + username.hashCode();
        return result;
    }
    public boolean isCorrect(){
        if(name == null || surname==null || birthday == null || gender == null || username ==null || password ==null || email == null || mobile_number == null || geographicUnits == null){
            return false;
        }
        return true;
    }
    public List<Param> toParam(){

        if(this != null){
            List<Param> params = new ArrayList<>();
            if(getId() != 0)params.add(new Param("id",Long.toString(getId())));
            params.add(new Param("name",getName()));
            params.add(new Param("surname",getSurname()));
            params.add(new Param("birthday",getBirthday()));
            params.add(new Param("gender",getGender()));
            params.add(new Param("email",getEmail()));
            params.add(new Param("username",getUsername()));
            params.add(new Param("password",getPassword()));
            params.add(new Param("geo_unit",Integer.toString(getGeographicUnits().getId())));
            params.add(new Param("mobile_number",getPhoneNumber()));
            return params;
        }
        return null;
    }
    public boolean checkIfLogedIn(Context context){
        Object obj = MyFiles.readFromFIle(context, MyFiles.Files.USER_STATE);
        if(obj != null){
            String state = obj.toString();
            String[] data = state.split(":");
            return data.length>0?Boolean.valueOf(data[1]):false;
        }
        else {
            return false;
        }
    }
    public void logIn(Context context){
        Log.i("VMUser: ",getUsername()+":"+Boolean.toString(true));
        MyFiles.writeInFile(context, MyFiles.Files.USER_STATE,getUsername()+":"+Boolean.toString(true));
        state = true;
    }
    public void logOut(Context context){
        MyFiles.writeInFile(context, MyFiles.Files.USER_STATE,getUsername()+":"+Boolean.toString(false));
        state = false;
    }
    public List<Video> seenVideos(List<Video> videos){
        List<Video> seenList = new ArrayList<>();
        for (Video vid:videos) {
            if(vid.getSeen() == 1){
                seenList.add(vid);
            }
        }
        return seenList;
    }
}
