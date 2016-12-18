package hr.videomarketing.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;


import hr.videomarketing.Models.Converts;

/**
 * Created by bagy on 25.11.2016..
 */

public class UserStatus implements Converts{
    private String earned;
    private String spent;

    public UserStatus(String earned, String spent) {
        this.earned = earned;
        this.spent = spent;
    }

    public UserStatus() {
        createNullDefinedObject();
    }

    public static UserStatus newInstance(JSONObject jsonObject) throws JSONException{
        if(jsonObject == null) return null;
        UserStatus status = new UserStatus(jsonObject.getString("earned"),jsonObject.getString("spent"));
        return status;
    }

    public String getEarned() {
        return earned;
    }

    public void setEarned(String earned) {
        this.earned = earned;
    }

    public String getSpent() {
        return spent;
    }

    public void setSpent(String spent) {
        this.spent = spent;
    }

    @Override
    public String toJSON() {
        return "{\"earned\":\""+earned+'\"'+
                ", \"spent\":\""+spent+'\"'+
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStatus)) return false;

        UserStatus status = (UserStatus) o;
        if (getEarned() != null ? !getEarned().equals(status.getEarned()) : status.getEarned() != null)
            return false;
        return getSpent() != null ? getSpent().equals(status.getSpent()) : status.getSpent() == null;

    }

    @Override
    public UserStatus createNullDefinedObject() {
        setEarned("");
        setSpent("");
        return this;
    }

    @Override
    public boolean isNullObject() {
        return equals(new UserStatus());
    }
}
