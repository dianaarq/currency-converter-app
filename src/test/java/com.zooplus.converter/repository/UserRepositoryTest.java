package com.zooplus.converter.repository;

import com.zooplus.converter.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


/**
 * Created by dianaarq on 08/07/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {

        @Autowired
        private UserRepository userRepository;

        @Test
        public void findByUsernameTest() {
            User user = userRepository.findByUsername("diana");
            Assert.isTrue(user.getUsername().equals("diana"));
        }

}
