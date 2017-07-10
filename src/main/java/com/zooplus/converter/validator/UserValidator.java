package com.zooplus.converter.validator;

import com.zooplus.converter.model.Rate;
import com.zooplus.converter.model.User;
import com.zooplus.converter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by dianaarq on 04/07/2017.
 */
@Component
public class UserValidator implements Validator {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String DATE_OF_BIRTH = "dateOfBirth";
    public static final String ZIP = "zip";
    public static final String EMAIL = "email";
    public static final String CITY = "city";
    public static final String ADDRESS = "address";
    public static final String PASSWORD_CONFIRM = "passwordConfirm";
    public static final String NOT_EMPTY = "NotEmpty";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern =  Pattern.compile(EMAIL_PATTERN);

    private static final String DATE_PATTERN = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
    private Pattern patternDate =  Pattern.compile(DATE_PATTERN);

    private final UserService userService;

    @Autowired
    public UserValidator(final UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }


    @Override
    public void validate(final Object o, final Errors errors) {
        User user = (User) o;


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, USERNAME, NOT_EMPTY);
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue(USERNAME, "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue(USERNAME, "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PASSWORD, NOT_EMPTY);
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue(PASSWORD, "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue(PASSWORD_CONFIRM, "Diff.userForm.passwordConfirm");
        }

        ValidationUtils.rejectIfEmpty(errors, ADDRESS, NOT_EMPTY);
        ValidationUtils.rejectIfEmpty(errors, CITY, NOT_EMPTY);


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, EMAIL, NOT_EMPTY);
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches()) {
            errors.rejectValue(EMAIL, "Format.userForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, DATE_OF_BIRTH, NOT_EMPTY);
        matcher = patternDate.matcher(user.getDateOfBirth());
        if (!matcher.matches()) {
            errors.rejectValue(DATE_OF_BIRTH, "Format.userForm.dateOfBirth");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, ZIP, NOT_EMPTY);
        if (user.getZip().length() < 1 || user.getPassword().length() > 8) {
            errors.rejectValue(ZIP, "Size.userForm.zip");
        }


    }
}
