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
    @Autowired
    private UserService userService;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern =  Pattern.compile(EMAIL_PATTERN);

    private static final String DATE_PATTERN = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
    private Pattern patternDate =  Pattern.compile(DATE_PATTERN);

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }


    @Override
    public void validate(final Object o, final Errors errors) {
        User user = (User) o;


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

        ValidationUtils.rejectIfEmpty(errors, "address", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "city", "NotEmpty");


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches()) {
            errors.rejectValue("email", "Format.userForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "NotEmpty");
        matcher = patternDate.matcher(user.getDateOfBirth());
        if (!matcher.matches()) {
            errors.rejectValue("dateOfBirth", "Format.userForm.dateOfBirth");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zip", "NotEmpty");
        if (user.getZip().length() < 1 || user.getPassword().length() > 8) {
            errors.rejectValue("zip", "Size.userForm.zip");
        }


    }
}
