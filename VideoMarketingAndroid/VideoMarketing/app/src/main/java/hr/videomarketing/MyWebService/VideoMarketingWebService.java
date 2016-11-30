package hr.videomarketing.MyWebService;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import hr.videomarketing.MyWebService.Interfaces.ConversionFailed;
import hr.videomarketing.MyWebService.Utils.WebService;
import hr.videomarketing.MyWebService.Utils.WebServiceException;

import static hr.videomarketing.MyWebService.Utils.WebServiceException.CONVERT_TO_JSON_EXCEPTION;
import static hr.videomarketing.MyWebService.Utils.WebServiceException.ERROR_ON_READING_DATA;
import static hr.videomarketing.MyWebService.Utils.WebServiceException.NO_INTERNET_CONNECTION;
import static hr.videomarketing.MyWebService.Utils.WebServiceException.WRONG_SERVICE_PATH;


/**
 * Created by bagy on 23.11.2016..
 */

public abstract class VideoMarketingWebService extends WebService implements ConversionFailed{
    private final String SERVER_PATH="http://www.videomarketdemo.esy.es/webservice/";
    @Override
    public String serviceName() {
        return SERVER_PATH+getVideoMarketingServicePath();
    }
    protected void log(String text){
        Log.i("VideoMarketingApp","bagy94>Services>"+text);
    }
    public abstract String getVideoMarketingServicePath();
    protected Context getContextFromListener(Object object){
        if(object instanceof Fragment) return ((Fragment) object).getActivity();
        else if(object instanceof Activity)return (Activity)object;
        else if(object instanceof Context)return (Context)object;
        return null;
    }

    @Override
    protected void serviceUnsuccessful(WebServiceException error,String retrievedData) {
        if(error.equals(NO_INTERNET_CONNECTION)){
            log("WebServiceException>no internet connection");
            Toast.makeText(getContext(),"Provjerite internet konekciju",Toast.LENGTH_LONG).show();

        }
        else if(error.equals(ERROR_ON_READING_DATA)){
            log("WebServiceException>data reading error");
            Toast.makeText(getContext(),"Greška prilikom čitanja dohvaćenih podataka, molimo pokušajte ponovno",Toast.LENGTH_LONG).show();
        }
        else if(error.equals(WRONG_SERVICE_PATH)){
            log("WebServiceException>Service isn't correct");
            Toast.makeText(getContext(),"Servis neispravan",Toast.LENGTH_LONG).show();
        }
        else if(error.equals(CONVERT_TO_JSON_EXCEPTION)){
            log("WebServiceException>casting to json incorrect, check received data: "+retrievedData);
        }
        if (error.getException()!=null){
            log("WebServiceExceptionError>"+error.getException().getMessage());
        }
    }

    @Override
    public void onJSONConversionError(JSONException js) {
        Context con = getContext();
        if (con !=null){
            Toast.makeText(con,"Došlo je do pogreške, molimo pokušajte ponovno",Toast.LENGTH_SHORT).show();
            log("Webservices>jsonconversion error: "+js.getMessage());
        }
    }

    @Override
    public void onListenerNull(WebService w) {
        Context con = getContext();
        if (con !=null){
            log("WebServices>"+w.getClass().getName()+">Listener is null");
        }
    }

    protected abstract Context getContext();
}
