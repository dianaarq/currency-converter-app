package com.zooplus.converter.web;


import com.zooplus.converter.model.Rate;
import com.zooplus.converter.model.User;
import com.zooplus.converter.service.CurrencyService;
import com.zooplus.converter.service.SecurityService;
import com.zooplus.converter.service.UserService;
import com.zooplus.converter.validator.RateValidator;
import com.zooplus.converter.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static com.zooplus.converter.web.UserController.USER_FORM;

/**
 * Created by dianaarq on 08/07/2017.
 */
public class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private SecurityService securityService;
    @Mock
    private UserValidator userValidator;
    @Mock
    private RateValidator rateValidator;
    @Mock
    private CurrencyService currencyService;

    private UserController userController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService,
                securityService, userValidator, rateValidator, currencyService);
    }

    @Test
    public void registration() throws Exception {
        Model model = Mockito.mock(Model.class);
        String output = userController.registration(model);
        Assert.isTrue(output.equals("registration"));
    }

    @Test
    public void error() throws Exception {
        Model model = Mockito.mock(Model.class);
        String output = userController.error(model);
        Assert.isTrue(output.equals("error"));
    }

    @Test
    public void getConversion() throws Exception {
        Model model = Mockito.mock(Model.class);
        Rate currencyForm = new Rate();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        String output = userController.getConversion(currencyForm, model, bindingResult);
        Assert.isTrue(output.equals("/welcome"));

    }

    @Test
    public void registrationMoreParameters() throws Exception {
        Model model = Mockito.mock(Model.class);
        User userForm = new User();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        String output = userController.registration(userForm,
                bindingResult, model);
        Mockito.verify(userValidator).validate(userForm, bindingResult);
        Mockito.verify(userService).save(userForm);
        Mockito.verify(securityService).autologin(userForm.getUsername(),
                userForm.getPasswordConfirm());

        Assert.isTrue(output.equals("redirect:/welcome"));
    }

    @Test
    public void registrationMoreParametersError() throws Exception {
        Model model = Mockito.mock(Model.class);
        User userForm = new User();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        String output = userController.registration(userForm,
                bindingResult, model);

        Assert.isTrue(output.equals("registration"));
    }

    @Test
    public void login() throws Exception {
        Model model = Mockito.mock(Model.class);
        String error = "error";
        String logout = "logout";
        String output = userController.login(model, error, logout);
        Mockito.verify(model).addAttribute("error", "Your username and password is invalid.");
        Mockito.verify(model).addAttribute("message", "You have been logged out successfully.");
        Assert.isTrue(output.equals("login"));

        error = null;
        logout = null;
        output = userController.login(model, error, logout);
        Assert.isTrue(output.equals("login"));
    }

    @Test
    public void welcome() throws Exception {
    }

}
