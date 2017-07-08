package com.zooplus.converter.service;

import com.zooplus.converter.model.CurrencyConverter;
import com.zooplus.converter.model.FixerConverter;

/**
 * Created by dianaarq on 04/07/2017.
 */
public interface CurrencyService {
    FixerConverter getRateByCurrency(String base, String currency, String date);
}
