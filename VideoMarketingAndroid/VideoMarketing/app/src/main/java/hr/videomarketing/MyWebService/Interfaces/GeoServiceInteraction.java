package hr.videomarketing.MyWebService.Interfaces;

import java.util.List;

import hr.videomarketing.Models.BaseModel.GeographicUnits;

/**
 * Created by bagy on 30.11.2016..
 */

public interface GeoServiceInteraction {
    void geoServiceDone(List<GeographicUnits> geographicUnitsList);
}
