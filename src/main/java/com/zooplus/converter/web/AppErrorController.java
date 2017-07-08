package com.zooplus.converter.web;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;

/**
 * Created by dianaarq on 07/07/2017.
 */
@Controller
public class AppErrorController implements ErrorController{
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
