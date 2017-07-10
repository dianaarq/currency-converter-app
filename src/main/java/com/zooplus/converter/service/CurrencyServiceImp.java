package com.zooplus.converter.service;

import com.zooplus.converter.model.FixerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by dianaarq on 07/07/2017.
 */
public class CurrencyServiceImp implements CurrencyService{

    private RestTemplate restTemplate;

    private static final String serviceUrl = "http://api.fixer.io/latest?" ;
    private static final String SYMBOLS = "symbols";
    private static final String BASE = "base";
    private static final String DATE = "date";

    @Override
    public FixerConverter getRateByCurrency(final String base, final String currency, final String date) {
        Map<String, String> mapCurrency = new HashMap<>();
        mapCurrency.put(SYMBOLS, currency);
        mapCurrency.put(BASE, base);
        mapCurrency.put(DATE, date);
        restTemplate = new RestTemplate();

        FixerConverter rate = restTemplate.getForObject(serviceUrl
                + "base={base}&symbols={symbols}&date={date}", FixerConverter.class, mapCurrency);
        return rate;
    }

}
