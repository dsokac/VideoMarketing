package hr.videomarketing.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bagy on 25.11.2016..
 */

public class UserStatus{
    User user;
    String user_id_remote;
    int points;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUser_id_remote() {
        return user_id_remote;
    }

    public void setUserIdRemote(String user_id_remote) {
        this.user_id_remote = user_id_remote;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public UserStatus() {
    }

    public UserStatus(User user, String user_id_remote, int points) {
        this.user = user;
        this.user_id_remote = user_id_remote;
        this.points = points;
    }

    public static UserStatus newInstance(JSONObject jsonStatus, User user) throws JSONException {
        return newInstance(user,jsonStatus.getInt("points"),jsonStatus.getString("user_id_remote"));
    }


    public static UserStatus newInstance(User user, int points, String user_id_remote){
        UserStatus userStatus = new UserStatus();
        userStatus.setUser(user);
        userStatus.setPoints(points);
        userStatus.setUserIdRemote(user_id_remote);
        return userStatus;
    }
}
