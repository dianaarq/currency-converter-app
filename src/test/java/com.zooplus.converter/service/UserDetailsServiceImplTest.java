package com.zooplus.converter.service;

import com.zooplus.converter.model.User;
import com.zooplus.converter.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by dianaarq on 10/07/2017.
 */
public class UserDetailsServiceImplTest {

    UserDetailsServiceImpl userDetailsService;
    @Mock
    UserRepository userRepository;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
       userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    public void loadUserByUsername() throws Exception {
        User user = mock(User.class);
        Mockito.when(userRepository.findByUsername("username")).thenReturn(user);
        Mockito.when(user.getUsername()).thenReturn("username");
        Mockito.when(user.getPassword()).thenReturn("password");
        Assert.notNull(userDetailsService.loadUserByUsername("username"));
    }

}