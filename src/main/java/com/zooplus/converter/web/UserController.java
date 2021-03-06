package com.zooplus.converter.web;

import com.zooplus.converter.model.FixerConverter;
import com.zooplus.converter.model.Rate;
import com.zooplus.converter.model.User;
import com.zooplus.converter.service.CurrencyService;
import com.zooplus.converter.service.CurrencyServiceImp;
import com.zooplus.converter.service.SecurityService;
import com.zooplus.converter.service.UserService;
import com.zooplus.converter.validator.RateValidator;
import com.zooplus.converter.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by dianaarq on 04/07/2017.
 */
@Controller
public class UserController {
    public static final String CURRENCY_FORM = "currencyForm";
    public static final String USER_FORM = "userForm";
    public static final String RATES = "rates";

    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;
    private final RateValidator rateValidator;
    private CurrencyService currencyService;

    @Autowired
    public UserController(final UserService userService, final SecurityService securityService,
                          final UserValidator userValidator, final RateValidator rateValidator)
                           {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.rateValidator = rateValidator;
    }

    public UserController(final UserService userService, final SecurityService securityService,
                          final UserValidator userValidator, final RateValidator rateValidator,
                          final CurrencyService currencyService)
    {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.rateValidator = rateValidator;
        this.currencyService = currencyService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(final Model model) {
        model.addAttribute(USER_FORM, new User());

        return "registration";
    }

    @RequestMapping(value = "/error")
    public String error(final Model model) {
        model.addAttribute(CURRENCY_FORM, new Rate());

        return "error";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.POST)
    public String getConversion(@ModelAttribute(CURRENCY_FORM) Rate currencyForm, final Model model,
                                final BindingResult bindingResult){
        rateValidator.validate(currencyForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/welcome";
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        FixerConverter fixerConverter;
        currencyService = new CurrencyServiceImp();
        fixerConverter = currencyService.getRateByCurrency(currencyForm.getBase(), currencyForm.getCurrency(), currencyForm.getDate());

        User user = userService.findByUsername(name);

        userService.saveRate(fixerConverter, user, currencyForm.getCurrency(), currencyForm.getDate());
        List<Object[]> objects = userService.findTop10byRate(name);

        List<Rate> rates = getRates(objects);

        model.addAttribute(RATES, rates);

        return "welcome";

    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute(USER_FORM) User userForm, final BindingResult bindingResult, final Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(final Model model, final String error, final String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(final Model model) {

        if( SecurityContextHolder.getContext().getAuthentication().getName() != null) {

            List<Object[]> objects = userService.findTop10byRate(SecurityContextHolder.getContext().getAuthentication().getName());
            List<Rate> rates = getRates(objects);
            model.addAttribute(RATES, rates);
            model.addAttribute(CURRENCY_FORM, new Rate());
        }
        return "welcome";
    }

     private List<Rate> getRates(final List<Object[]> objects) {
        List<Rate> rates = new ArrayList<>();
        if( objects.size() > 0 ) {
            for (Object[] item : objects) {
                Rate rate = new Rate();

                rate.setId(((BigInteger) item[0]).longValue());
                rate.setBase((String) item[1]);
                rate.setCurrency((String) item[2]);
                rate.setExchange((String) item[3]);
                rate.setDate((String) item[4]);
                rates.add(rate);
            }

        }
        return rates;
    }
}
