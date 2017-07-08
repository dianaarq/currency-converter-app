package com.zooplus.converter.repository;

import com.zooplus.converter.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by dianaarq on 08/07/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class RateRepositoryTest {

    @Autowired
    private RateRepository rateRepository;

    @Test
    public void findTop10byRateTest() {
        List<Object[]>  rates = rateRepository.findTop10byRate("diana");
        Assert.isTrue(rates.get(0).length > 0);
        Assert.isTrue(rates.get(0).length == 6);
    }
}
