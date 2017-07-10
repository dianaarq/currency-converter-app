package com.zooplus.converter.validator;

import com.zooplus.converter.model.User;
import com.zooplus.converter.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import static com.zooplus.converter.validator.UserValidator.DATE_OF_BIRTH;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by dianaarq on 10/07/2017.
 */
public class UserValidatorTest {
    @Mock
    UserService userService;

    UserValidator userValidator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userValidator = new UserValidator(userService);
    }

    @Test
    public void validate() throws Exception {
        User user = mock(User.class);
        Errors errors = mock(Errors.class);
        Mockito.when(user.getUsername()).thenReturn("dianaArq");
        Mockito.when(user.getPassword()).thenReturn("password");
        Mockito.when(user.getPasswordConfirm()).thenReturn("password");
        Mockito.when(user.getEmail()).thenReturn("diana.arquillos@gmail.com");
        Mockito.when(userService.findByUsername(user.getUsername())).thenReturn(null);
        Mockito.when(user.getAddress()).thenReturn("Address");
        Mockito.when(user.getDateOfBirth()).thenReturn("29-10-1984");
        Mockito.when(user.getZip()).thenReturn("23008");
        userValidator.validate(user, errors);
        Mockito.verify(errors).rejectValue(DATE_OF_BIRTH, "Format.userForm.dateOfBirth");
    }

}