package com.zooplus.converter.service;

import com.zooplus.converter.model.FixerConverter;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

public class CurrencyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private CurrencyService currencyService;

    public CurrencyServiceTest() {
        MockitoAnnotations.initMocks(this);
        currencyService = new CurrencyServiceImp();
    }

    //@Test
    public void getRateByCurrencyTest() {
        String base="EUR";
        String currency="DOL";
        String date="2017-03-12";
        FixerConverter converter = new FixerConverter();
        Mockito.when(restTemplate.getForObject(Matchers.anyString(), Matchers.eq(FixerConverter.class), Matchers.anyMapOf(String.class, String.class)))
                .thenReturn(converter);
        FixerConverter fixerConverter = currencyService.getRateByCurrency(base,currency,date);
        assertEquals(fixerConverter, converter);
    }
}