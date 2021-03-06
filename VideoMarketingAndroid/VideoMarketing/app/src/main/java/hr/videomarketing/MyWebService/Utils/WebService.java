package hr.videomarketing.MyWebService.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bagy on 28.10.2016..
 */

public abstract class WebService<T,E,G> extends AsyncTask<T,E,G> implements WebServiceInterface{

    private URL urlC;
    private HttpURLConnection httpURLConnection;
    private String service;
    private StringBuffer strngBuffer;
    private InputStream is;
    private BufferedReader bufferReader;
    private Param[] params ;
    private ProgressDialog progressDialog;
    protected JSONObject result=null;
    protected String retrievedData=null;
    private boolean closeProgresssDialog = true;
    private WebServiceException error = null;
    @Override
    protected G doInBackground(T... voids) {
        try {
            if(getContext() != null && !haveNetworkconnection(getContext())){
                serviceUnsuccessful(new WebServiceException(WebServiceException.NO_INTERNET_CONNECTION),"");
                return null;
            }
            result = GET();
        }catch (WebServiceException e){
            error = e;
            serviceUnsuccessful(e,retrievedData!=null?retrievedData:"");
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        service = serviceName();
        params = serviceParam();
        if(progressDialog != null){
            progressDialog.show();
        }
    }

    @Override
    protected void onPostExecute(G o) {
        super.onPostExecute(o);
        if(progressDialog !=null && closeProgresssDialog){
            progressDialog.dismiss();
        }
        if(result != null){
            serviceDone(result);
        }
        else{
            serviceUnsuccessful(error,retrievedData);
        }
    }

    @Override
    public String POST() {
        //TODO: implement post method
        return null;
    }

    @Override
    public JSONObject GET() {
        if(service.equalsIgnoreCase("")){
            throw new WebServiceException(WebServiceException.WRONG_SERVICE_PATH);
        }
        String url =service+convertParamToUrl();

        log("url: "+url);
        try {
            urlC = new URL(url);
            httpURLConnection = (HttpURLConnection) urlC.openConnection();
            is = httpURLConnection.getInputStream();
            bufferReader = new BufferedReader(new InputStreamReader(is));
            strngBuffer = new StringBuffer();
            String line ="";

            while((line =bufferReader.readLine())!=null){
                strngBuffer.append(line).append('\n');
            }
            bufferReader.close();
            is.close();
            retrievedData = strngBuffer.toString();
            log("result>"+retrievedData);
            return new JSONObject(retrievedData);
        } catch (MalformedURLException e) {
            throw new WebServiceException(WebServiceException.WRONG_SERVICE_PATH,e);
        }catch (IOException e){
            throw new WebServiceException(WebServiceException.ERROR_ON_READING_DATA,e);
        }catch (JSONException json){
            result = null;
            throw new WebServiceException(WebServiceException.CONVERT_TO_JSON_EXCEPTION,json);
        }
        finally {
            httpURLConnection.disconnect();
        }
    }

    public boolean haveNetworkconnection(Context context){
        boolean haveWifi = false;
        boolean haveMobileConnection = false;
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = conManager.getAllNetworkInfo();
        for(NetworkInfo i:netInfo){
            if(i.isConnected()){
                if(i.getType() == ConnectivityManager.TYPE_WIFI){haveWifi=true;}
                else if(i.getType() == ConnectivityManager.TYPE_MOBILE){
                    haveMobileConnection = true;
                }
            }
        }
        return haveWifi || haveMobileConnection;
    }
    private String convertParamToUrl(){
        String url = "";
        if(params != null){
            url = "?";
            for (int i = 0; i < params.length; i++) {
                url += params[i].toString();
                if(!(i==params.length-1)){
                    url+="&";
                }
            }
        }
        return url;
    }
    public void setProgressDialog(String message){
        if(getContext() != null){
            progressDialog = new ProgressDialog(getContext());
            if(message.equals("") || message == null){
                progressDialog.setMessage("Dohvaćam podatke");
            }
            else {
                progressDialog.setMessage(message);
            }
            progressDialog.setCancelable(false);
        }else {
            progressDialog = null;
        }
    }
    public void setProgressDialog(String message, boolean closeProgresssDialog){
        setProgressDialog(message);
        this.closeProgresssDialog = closeProgresssDialog;
    }
    protected void closeProgresssDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
    protected void log(String text){
        Log.i("VideoMarketingApp","bagy94>WebService>"+text);
    }
    protected abstract void serviceDone(JSONObject result);
    protected abstract void serviceUnsuccessful(WebServiceException error,String result);
    protected abstract String serviceName();
    protected abstract Param[] serviceParam();
}
