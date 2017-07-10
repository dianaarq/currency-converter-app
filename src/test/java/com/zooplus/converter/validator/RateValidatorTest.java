package com.zooplus.converter.validator;

import com.zooplus.converter.model.Rate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import static com.zooplus.converter.validator.RateValidator.DATE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by dianaarq on 10/07/2017.
 */
public class RateValidatorTest {
    RateValidator rateValidator = new RateValidator();
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void validate() throws Exception {
        Errors errors = mock(Errors.class);
        Rate rate = mock(Rate.class);
        Mockito.when(rate.getDate()).thenReturn("29-06-2007");
        Mockito.when(rate.getCurrency()).thenReturn("USD");
        Mockito.when(rate.getBase()).thenReturn("EUR");
        rateValidator.validate(rate, errors);
        Mockito.verify(errors).rejectValue(DATE, "Format.currencyForm.date");
    }

}