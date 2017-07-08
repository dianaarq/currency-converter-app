package com.zooplus.converter.service;

import com.zooplus.converter.model.CurrencyConverter;
import com.zooplus.converter.model.FixerConverter;
import com.zooplus.converter.model.Rate;
import com.zooplus.converter.model.User;
import com.zooplus.converter.repository.RateRepository;
import com.zooplus.converter.repository.RoleRepository;
import com.zooplus.converter.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
/**
 * Created by dianaarq on 04/07/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void save(final User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public void saveRate(final FixerConverter currencyConverter, final User user, final String currency, final String date) {
        String quote = currencyConverter.getRates().get(currency).toString();
        String base = currencyConverter.getBase();
        rateRepository.save(new Rate(currency, base, quote, date, user));
    }

    @Override
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Object[]> findTop10byRate(final String username) {
        return rateRepository.findTop10byRate(username);
    }


}
