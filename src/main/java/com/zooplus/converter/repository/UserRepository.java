package com.zooplus.converter.repository;

import com.zooplus.converter.model.Rate;
import com.zooplus.converter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * Created by dianaarq on 04/07/2017.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
