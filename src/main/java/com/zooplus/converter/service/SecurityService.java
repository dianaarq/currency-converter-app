package com.zooplus.converter.service;
/**
 * Created by dianaarq on 04/07/2017.
 */
public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
