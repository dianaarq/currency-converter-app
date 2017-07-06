package com.zooplus.converter.service;

import com.zooplus.converter.model.CurrencyConverter;

/**
 * Created by dianaarq on 04/07/2017.
 */
public interface CurrencyService {
    CurrencyConverter getRateByCurrency(String currency);
}
