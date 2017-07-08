package com.zooplus.converter.service;

import com.zooplus.converter.model.CurrencyConverter;
import com.zooplus.converter.model.FixerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dianaarq on 07/07/2017.
 */
public class CurrencyServiceFixerImp implements CurrencyService{

    private RestTemplate restTemplate;

    private static final String serviceUrl = "http://api.fixer.io/latest?" ;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public FixerConverter getRateByCurrency(final String base, final String currency, final String date) {
        Map<String, String> mapCurrency = new HashMap<>();
        mapCurrency.put("symbols", currency);
        mapCurrency.put("base", base);
        mapCurrency.put("date", date);
        restTemplate = new RestTemplate();
        FixerConverter rate = restTemplate.getForObject(serviceUrl
                + "base={base}&symbols={symbols}&date={date}", FixerConverter.class, mapCurrency);
        LOGGER.info("CurrencyRate " + rate);
        return rate;
    }
}
