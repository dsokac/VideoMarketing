package hr.videomarketing.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;


import hr.videomarketing.Models.Converts;

/**
 * Created by bagy on 25.11.2016..
 */

public class UserStatus implements Converts{
    private String month;
    private String year;
    private String earned;
    private String spent;

    public UserStatus(String month, String year, String earned, String spent) {
        this.month = month;
        this.year = year;
        this.earned = earned;
        this.spent = spent;
    }

    public UserStatus() {
        createNullDefinedObject();
    }

    public static UserStatus newInstance(JSONObject jsonObject) throws JSONException{
        if(jsonObject == null) return null;
        UserStatus status = new UserStatus(jsonObject.getString("month"),jsonObject.getString("year"),jsonObject.getString("earned"),jsonObject.getString("spent"));
        return status;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
        return "{\"month\":\""+month+'\"'+
                ", \"year\":\""+year+'\"'+
                ", \"earned\":\""+earned+'\"'+
                ", \"spent\":\""+spent+'\"'+
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStatus)) return false;

        UserStatus status = (UserStatus) o;

        if (getMonth() != null ? !getMonth().equals(status.getMonth()) : status.getMonth() != null)
            return false;
        if (getYear() != null ? !getYear().equals(status.getYear()) : status.getYear() != null)
            return false;
        if (getEarned() != null ? !getEarned().equals(status.getEarned()) : status.getEarned() != null)
            return false;
        return getSpent() != null ? getSpent().equals(status.getSpent()) : status.getSpent() == null;

    }

    @Override
    public int hashCode() {
        int result = getMonth() != null ? getMonth().hashCode() : 0;
        result = 31 * result + (getYear() != null ? getYear().hashCode() : 0);
        result = 31 * result + (getEarned() != null ? getEarned().hashCode() : 0);
        result = 31 * result + (getSpent() != null ? getSpent().hashCode() : 0);
        return result;
    }

    @Override
    public UserStatus createNullDefinedObject() {
        setMonth("");
        setEarned("");
        setSpent("");
        setYear("");
        return this;
    }

    @Override
    public boolean isNullObject() {
        return equals(new UserStatus().createNullDefinedObject());
    }
}
