package hr.videomarketing.Models.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import hr.videomarketing.Models.Converts;

/**
 * Created by bagy on 31.10.2016..
 */

public class GeographicUnits implements Converts{
    private int id;
    private String name;

    public GeographicUnits(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public GeographicUnits(){
        createNullDefinedObject();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeographicUnits that = (GeographicUnits) o;

        if (id != that.id) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public GeographicUnits createNullDefinedObject() {
        setId(0);
        setName("");
        return this;
    }

    @Override
    public boolean isNullObject() {
        return equals(new GeographicUnits());
    }

    public static List<GeographicUnits> getListFromJsonArray(String units) {
        List<GeographicUnits> geographicUnitsList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(units);
            for (int i = 0; i < array.length(); i++) {
                geographicUnitsList.add(new GeographicUnits(array.getJSONObject(i).getInt("id"), array.getJSONObject(i).getString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return geographicUnitsList;
    }
}
