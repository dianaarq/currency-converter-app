package com.zooplus.converter.model;

import java.util.Map;

/**
 * Created by dianaarq on 07/07/2017.
 */
public class FixerConverter {

    private String base;
    private String date;
    private Map<String,Double> rates;
    
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }


}
