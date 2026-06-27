package com.fawry.springsecuritydemo.common.servlet;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class HttpRequestHeaderUtil {

    public String getHeader(String headerName) {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getHeader(headerName);
    }
}
