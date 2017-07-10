package com.zooplus.converter.service;

import com.zooplus.converter.model.FixerConverter;
import com.zooplus.converter.model.Rate;
import com.zooplus.converter.model.User;
import com.zooplus.converter.repository.RateRepository;
import com.zooplus.converter.repository.RoleRepository;
import com.zooplus.converter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
/**
 * Created by dianaarq on 04/07/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RateRepository rateRepository;


    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository,
                           final BCryptPasswordEncoder bCryptPasswordEncoder, final RateRepository rateRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.rateRepository = rateRepository;
    }

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
        Rate rate = new Rate(currency, base, quote, date, user);
        rateRepository.save(rate);
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
