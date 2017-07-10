package com.zooplus.converter.service;

import com.zooplus.converter.model.FixerConverter;
import com.zooplus.converter.model.Rate;
import com.zooplus.converter.model.Role;
import com.zooplus.converter.model.User;
import com.zooplus.converter.repository.RateRepository;
import com.zooplus.converter.repository.RoleRepository;
import com.zooplus.converter.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.*;

import static org.mockito.Matchers.anyListOf;

/**
 * Created by dianaarq on 10/07/2017.
 */
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private RateRepository rateRepository;

    UserService userService;
    @Before
    public void setUp() throws Exception {
            MockitoAnnotations.initMocks(this);
            userService = new UserServiceImpl(userRepository, roleRepository,
                    bCryptPasswordEncoder, rateRepository);
    }

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setPassword("password");
        user.setRoles(new HashSet<Role>());
        Mockito.when(bCryptPasswordEncoder.encode(Matchers.isNotNull(CharSequence.class)))
                .thenReturn("passwordEncoder");
        Mockito.when(roleRepository.findAll()).thenReturn(new ArrayList<Role>());
        userService.save(user);
        Mockito.verify(userRepository).save(user);

    }

    @Test
    public void saveRate() throws Exception {
        User user = new User();
        String currency = "currency";
        String date = "date";
        Map<String, Double> rate;
        rate = new HashMap<>();
        rate.put("currency",12.3);
        FixerConverter currencyConverter = new FixerConverter();
        currencyConverter.setRates(rate);
        currencyConverter.setBase("base");
        Rate r = new Rate(currency, "base", "12.3", date, user);
        Mockito.when(rateRepository.save(Matchers.any(Rate.class))).thenReturn(r);
        userService.saveRate(currencyConverter, user, currency, date);

    }

    @Test
    public void findByUsername() throws Exception {
        User user = new User();
        Mockito.when(userRepository.findByUsername("username")).thenReturn(user);
        Assert.isTrue(userService.findByUsername("username").equals(user));
    }

    @Test
    public void findTop10byRate() throws Exception {
        List<Object[]> rates = new ArrayList<>();
        Mockito.when(rateRepository.findTop10byRate("username")).
                thenReturn(rates);
        Assert.isTrue(userService.findTop10byRate("username").equals(rates));
    }

}