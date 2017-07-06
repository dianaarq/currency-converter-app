package com.zooplus.converter.service;

import com.zooplus.converter.model.CurrencyConverter;
import com.zooplus.converter.model.Rate;
import com.zooplus.converter.model.User;

import java.util.List;
/**
 * Created by dianaarq on 04/07/2017.
 */
public interface UserService {
    void save(User user);
    void saveRate(CurrencyConverter currencyConverter, User user, String currency, String date);
    User findByUsername(String username);
    List<Rate> findTop10byRate(String username);
}
