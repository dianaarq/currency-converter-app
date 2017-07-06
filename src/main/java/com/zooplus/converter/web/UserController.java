package com.zooplus.converter.web;

import com.zooplus.converter.model.CurrencyConverter;
import com.zooplus.converter.model.Rate;
import com.zooplus.converter.model.User;
import com.zooplus.converter.service.CurrencyService;
import com.zooplus.converter.service.SecurityService;
import com.zooplus.converter.service.UserService;
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
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public String getConversion(@ModelAttribute("currencyForm") Rate currencyForm) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        LOGGER.info("User name"+ name);
        List<Rate> rates = new ArrayList<>();
        rates = userService.findTop10byRate(name);
        LOGGER.info("size"+rates.size());
        if(rates.size()>0)
            LOGGER.info(""+rates.get(0).getId());
        CurrencyConverter currencyConverter =
                currencyService.getRateByCurrency(currencyForm.getCurrency());
        LOGGER.info("User name de quotes"+ currencyConverter.getQuotes().toString());

        User user = userService.findByUsername(name);
        LOGGER.info("User name from the user"+user.getUsername());

        userService.saveRate(currencyConverter, user, currencyForm.getCurrency(), currencyForm.getDate());


        return "welcome";

    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        if(SecurityContextHolder.getContext().getAuthentication().getName() != null){

            List<Rate> rates = new ArrayList<Rate>();
            rates = userService.findTop10byRate(SecurityContextHolder.getContext().getAuthentication().getName());
            
            model.addAttribute("rates", rates);
            model.addAttribute("currencyForm", new Rate());
        }
        return "welcome";
    }
}
