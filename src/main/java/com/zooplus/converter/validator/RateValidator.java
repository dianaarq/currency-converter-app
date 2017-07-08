package com.zooplus.converter.validator;

import com.zooplus.converter.model.Rate;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dianaarq on 07/07/2017.
 */

@Component
public class RateValidator implements Validator {

    private static final String DATE_PATTERN_FORM = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
    public static final String DATE = "date";
    private Pattern patternDateForm =  Pattern.compile(DATE_PATTERN_FORM);

    @Override
    public boolean supports(final Class<?> aClass) {
        return Rate.class.equals(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        Rate rate = (Rate) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, DATE, "NotEmpty");
        Matcher matcher = patternDateForm.matcher(rate.getDate());
        if (!matcher.matches()) {
            errors.rejectValue(DATE, "Format.currencyForm.date");
        }
    }
}
