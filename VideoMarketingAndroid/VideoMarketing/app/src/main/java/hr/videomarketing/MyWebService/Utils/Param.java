package hr.videomarketing.MyWebService.Utils;

import hr.videomarketing.Models.BaseClass;

/**
 * Created by bagy on 31.10.2016..
 */

public class Param implements BaseClass {
    private String key;
    private String value;
    private String[] fieldValues;
    private Param(){

    }

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public Param(String key, String[] fieldValues){
        setKey(key);
        setFieldValues(fieldValues);
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

    public String[] getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(String[] fieldValues) {
        this.fieldValues = fieldValues;
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

    @Override
    public boolean equals(Object object) {
        if(object instanceof Param){
            Param p = (Param) object;
            if (!getKey().equals(p.getKey()))return false;
            if (!getValue().equals(p.getValue()))return false;
            return true;
        }
        return false;
    }

    @Override
    public Param createNullDefinedObject() {
        setKey("");
        setValue("");
        return this;
    }

    @Override
    public boolean isNullObject() {
        return this.equals(new Param().createNullDefinedObject());
    }
}
