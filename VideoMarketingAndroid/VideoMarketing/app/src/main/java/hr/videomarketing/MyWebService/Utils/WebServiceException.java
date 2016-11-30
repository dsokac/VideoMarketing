package hr.videomarketing.MyWebService.Utils;

/**
 * Created by bagy on 6.11.2016..
 */
public class WebServiceException extends RuntimeException{
    public final static String NO_INTERNET_CONNECTION = "No internet connection";
    public final static String WRONG_SERVICE_PATH = "Service path worng";
    public final static String ERROR_ON_READING_DATA = "Error upon reading data";
    public final static String LISTENER_CAST_EXCEPTION="Service listener is null, service:";
    public final static String CONVERT_TO_JSON_EXCEPTION = "Cast string as json exception";
    Exception exception = null;
    private String error;
    public WebServiceException(String message){
        super(message);
        error = message;
    }
    public WebServiceException(String message,Exception e){
        super(message);
        error = message;
        exception = e;
    }

    @Override
    public String getMessage() {
        return error;
    }
    public Exception getException(){return exception;}

    @Override
    public boolean equals(Object obj) {
        if(error.equals(obj.toString())){
            return true;
        }
        else {
            return false;
        }
    }
}

