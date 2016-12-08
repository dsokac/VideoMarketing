package hr.videomarketing.MyWebService.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import hr.videomarketing.Models.BaseModel.GeographicUnits;
import hr.videomarketing.MyWebService.Interfaces.GeoServiceInteraction;
import hr.videomarketing.MyWebService.Utils.Param;
import hr.videomarketing.MyWebService.VideoMarketingWebService;


/**
 * Created by bagy on 23.11.2016..
 */

public class GeographicUnitsService extends VideoMarketingWebService {
    GeoServiceInteraction myListener;

    public GeographicUnitsService(GeoServiceInteraction myListener) {
        super(myListener);
        this.myListener = myListener;
    }
    public GeographicUnitsService(GeoServiceInteraction myListener,String progressDialogMessage) {
        super(myListener);
        this.myListener = myListener;
        setProgressDialog(progressDialogMessage);
    }
    @Override
    public String getVideoMarketingServicePath() {
        return "geographic_units";
    }

    @Override
    protected void serviceDone(JSONObject result) {
        if(myListener == null){
            onListenerNull(this);
            return;
        }
        try{
            JSONArray units = result.getJSONArray("data");
            List<GeographicUnits> geographicUnitsList = new ArrayList<>();
            for (int i = 0; i < units.length(); i++) {
                geographicUnitsList.add(new GeographicUnits(units.getJSONObject(i).getInt("id"), units.getJSONObject(i).getString("name")));
            }
            myListener.geoServiceDone(geographicUnitsList);
        }catch (JSONException jsonE){
            onJSONConversionError(jsonE);
        }
    }

    @Override
    public Param[] serviceParam() {
        return null;
    }
}
