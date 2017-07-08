package com.zooplus.converter.service;

import com.zooplus.converter.model.FixerConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


/**
 * Created by dianaarq on 08/07/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class CurrencyServiceTest {
    @Autowired
    CurrencyService currencyService;
    @Test
    public void getRateByCurrencyTest() {
        String base="EUR";
        String currency="DOL";
        String date="2017-03-12";
        FixerConverter fixerConverter = currencyService.getRateByCurrency(base,currency,date);
        Assert.isTrue(fixerConverter.getBase().equals(base));
        Assert.isTrue(fixerConverter.getDate().equals(date));
        Assert.isTrue(fixerConverter.getRates().containsKey(currency));
    }
}
