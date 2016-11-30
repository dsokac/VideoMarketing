package hr.videomarketing.MyWebService.Utils;

/**
 * Created by bagy on 31.10.2016..
 */

public class Param {
    private String key;
    private String value;

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if(value.contains(" ")){
            return key+"="+value.replaceAll(" ","");
        }
        return key+"="+ value;
    }
    public String toJSONString(){
        if(value.contains(" ")){
            return '"'+key+"\":\""+value.replaceAll(" ","")+'"';
        }
        return '"'+key+"\":\""+value+'"';
    }
}
