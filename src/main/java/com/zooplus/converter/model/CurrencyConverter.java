package com.zooplus.converter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dianaarq on 04/07/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyConverter implements Serializable{
    String success;
    String terms;
    String privacy;
    String timestamp;
    String source;
    Object quotes;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Object getQuotes() {
        return quotes;
    }

    public void setQuotes(Object quotes) {
        this.quotes = quotes;
    }
}
