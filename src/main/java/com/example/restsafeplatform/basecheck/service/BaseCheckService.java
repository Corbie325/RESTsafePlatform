package com.example.restsafeplatform.basecheck.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

@Slf4j
@Service
public class BaseCheckService {
    public boolean baseCheck(ServletRequest servletRequest){

        String str = servletRequest.getRemoteHost();
        return false;

        //TODO не забыть


    }
}
