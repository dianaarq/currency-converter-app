package com.zooplus.converter.service;

import com.zooplus.converter.model.CurrencyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dianaarq on 04/07/2017.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService{


    private RestTemplate restTemplate;

    private static final String serviceUrl = "http://apilayer.net/api/live?access_key=fd3bfa19a371c70aefcd67a46f941713&currencies=" ;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public CurrencyConverter getRateByCurrency(String currency) {
        Map<String, String> mapCurrency = new HashMap<>();
        mapCurrency.put("currency", currency);
        restTemplate = new RestTemplate();
        CurrencyConverter rate = restTemplate.getForObject(serviceUrl
                + "{currency}", CurrencyConverter.class, mapCurrency);
        LOGGER.info("CurrencyRate " + rate);
        return rate;
    }
}
