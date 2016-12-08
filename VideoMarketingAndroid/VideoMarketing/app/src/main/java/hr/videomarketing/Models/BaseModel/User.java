package hr.videomarketing.Models.BaseModel;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hr.videomarketing.Models.Converts;
import hr.videomarketing.Utils.MyFiles;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.VideoMarketingApp;

/**
 * Created by bagy on 31.10.2016..
 */

public class User implements Converts{
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
    private boolean logedIn = false;
    private List<UserStatus> pointStatus;

    private User(long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public User(){
        createNullDefinedObject();
    }

    public static User newInstance(String jsonUser){
        if(jsonUser == null){
            return new User();
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
            if(jsonObject.has("logedIn"))user.setLogedIn(jsonObject.getBoolean("logedIn"));
            user.setGeographicUnits(new GeographicUnits(jsonObject.getJSONObject("geographic_unit").getInt("id"),jsonObject.getJSONObject("geographic_unit").getString("name")));
            if(user.isCorrect()){
                Log.i("videomark/User: ","newInstance: "+user.toString());
                return user;
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return new User();
    }


    public void setPhoneNumber(String phoneNumber) {
        this.mobile_number = phoneNumber.contains("+")?phoneNumber.replace("+",Integer.toString(0)):phoneNumber;
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

    public String getPhoneNumber() {
        return mobile_number;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public GeographicUnits getGeographicUnits() {
        return geographicUnits;
    }

    public void setPointStatus(List<UserStatus> pointStatus) {
        this.pointStatus = pointStatus;
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
        if (!name.equals(user.name)) return false;
        if (!birthday.equals(user.birthday)) return false;
        if (!gender.equals(user.gender)) return false;
        if (geographicUnits.getId() != user.geographicUnits.getId()) return false;
        if (!mobile_number.equals(user.mobile_number)) return false;
        return email.equals(user.email);

    }

    @Override
    public String toString() {
        return toJSON();
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
            if(!getPassword().equals(""))params.add(new Param("password",getPassword()));
            params.add(new Param("geo_unit",Integer.toString(getGeographicUnits().getId())));
            params.add(new Param("mobile_number",getPhoneNumber()));
            log("phone number to param>"+getPhoneNumber());
            return params;
        }
        return null;
    }

    public boolean isLogedIn() {
        return logedIn;
    }

    public void setLogedIn(boolean logedIn) {
        this.logedIn = logedIn;
    }
    public List<UserStatus> getPointStatus(){
        return pointStatus;
    }
    public void setStatus(JSONArray jsonArray){
        this.pointStatus = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try{
                this.pointStatus.add(UserStatus.newInstance(jsonArray.getJSONObject(i)));
            }catch (JSONException je){
                je.printStackTrace();
            }
        }
    }

    @Override
    public String toJSON() {
        return !this.isNullObject()?'{' +
                "\"id\":"+ id +
                ",\"name\":\"" + name + '\"' +
                ",\"surname\":\"" + surname + '\"' +
                ",\"birthday\":\"" + birthday + '\"' +
                ",\"gender\":\"" + gender + '\"' +
                ",\"username\":\"" + username + '\"' +
                ",\"password\":\"" + password + '\"' +
                ",\"email\":\"" + email + '\"' +
                ",\"logedIn\":\""+Boolean.toString(logedIn)+'\"'+
                ",\"mobile_number\":\"" + mobile_number + '\"' +
                ",\"geographic_unit\":{"+
                "\"id\":"+ geographicUnits.getId()+
                ",\"name\":\""+geographicUnits.getName()+"\"}}"
                :"";
    }
    private void log(String txt){
        VideoMarketingApp.log("UserClass>"+txt);
    }

    @Override
    public User createNullDefinedObject() {
        setId(0);
        setName("");
        setSurname("");
        setBirthday("");
        setGeographicUnits(new GeographicUnits());
        setGender("");
        setPhoneNumber("");
        setUsername("");
        setEmail("");
        setPassword("");
        return this;
    }

    @Override
    public User clone() {
        User newObject = new User();
        newObject.setId(getId());
        newObject.setName(getName());
        newObject.setSurname(getSurname());
        newObject.setBirthday(getBirthday());
        newObject.setGender(getGender());
        newObject.setUsername(getUsername());
        newObject.setEmail(getEmail());
        newObject.setPassword(getPassword());
        newObject.setPhoneNumber(getPhoneNumber());
        newObject.setGeographicUnits(getGeographicUnits());
        newObject.setLogedIn(isLogedIn());
        newObject.setPointStatus(getPointStatus());
        return newObject;
    }

    @Override
    public boolean isNullObject() {
        return this.equals(new User());
    }
}
