package com.zooplus.converter.web;

import com.zooplus.converter.model.CurrencyConverter;
import com.zooplus.converter.model.Rate;
import com.zooplus.converter.model.User;
import com.zooplus.converter.service.*;
import com.zooplus.converter.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by dianaarq on 04/07/2017.
 */
@Controller
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(final Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(final Model model) {
        model.addAttribute("userForm", new User());

        return "error";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.POST)
    public String getConversion(@ModelAttribute("currencyForm") Rate currencyForm, final Model model){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        LOGGER.info("getConversion");
        CurrencyConverter currencyConverter =
                currencyService.getRateByCurrency(currencyForm.getCurrency());
        LOGGER.info("User name de quotes"+ currencyConverter.getQuotes().toString());

        User user = userService.findByUsername(name);
        LOGGER.info("User name from the user" + user.getUsername());

        userService.saveRate(currencyConverter, user, currencyForm.getCurrency(), currencyForm.getDate());
        List<Object[]> objects = userService.findTop10byRate(name);

        List<Rate> rates = getRates(objects);

        model.addAttribute("rates", rates);

        return "welcome";

    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, final BindingResult bindingResult, final Model model) {
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
        LOGGER.info("welcome");

        if( SecurityContextHolder.getContext().getAuthentication().getName() != null){

            List<Object[]> objects = new ArrayList<>();
            objects = userService.findTop10byRate(SecurityContextHolder.getContext().getAuthentication().getName());
            List<Rate> rates = getRates(objects);
            model.addAttribute("rates", rates);
            model.addAttribute("currencyForm", new Rate());
        }
        return "welcome";
    }

    /*
*  private Long id;
    String currency;
    String exchange;
    Timestamp timestamp;
    String date;
    User user;*/
    private List<Rate> getRates(final List<Object[]> objects) {
        List<Rate> rates = new ArrayList<>();
        LOGGER.info("size" + objects.size());
        if( objects.size() > 0 ) {
            for (Object[] item : objects) {
                Rate rate = new Rate();

                LOGGER.info("" + item[0]);
                rate.setId(((BigInteger) item[0]).longValue());
                LOGGER.info("" + item[1]);
                rate.setCurrency((String) item[1]);
                LOGGER.info("" + item[2]);
                rate.setExchange((String) item[2]);

                LOGGER.info("" + item[3]);
                rate.setTimestamp((Timestamp) item[3]);

                LOGGER.info("" + item[4]);
                rate.setDate((String) item[4]);

                rates.add(rate);
            }

        }
        return rates;
    }
}
