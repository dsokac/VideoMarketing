package hr.videomarketing.MyWebService.Utils;
import android.content.Context;

import org.json.JSONObject;

/**
 * Created by bagy on 23.11.2016..
 */
public interface WebServiceInterface {
    JSONObject GET();
    String POST();
    Context getContext();
}
