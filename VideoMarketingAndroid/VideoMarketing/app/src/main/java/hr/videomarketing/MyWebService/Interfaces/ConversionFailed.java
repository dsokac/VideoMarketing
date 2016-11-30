package hr.videomarketing.MyWebService.Interfaces;

import org.json.JSONException;

import hr.videomarketing.MyWebService.Utils.WebService;

/**
 * Created by bagy on 30.11.2016..
 */

public interface ConversionFailed {
    void onJSONConversionError(JSONException e);
    void onListenerNull(WebService w);
}
