
package com.example.imageuploaderapp.schemes;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class WeatherData {

    private String cod;
    private Integer message;
    private Integer cnt;
    private java.util.List<com.example.imageuploaderapp.schemes.List> list = null;
    private City city;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public WeatherData() {
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<com.example.imageuploaderapp.schemes.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.example.imageuploaderapp.schemes.List> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
