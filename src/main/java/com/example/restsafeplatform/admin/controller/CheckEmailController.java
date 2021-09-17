package com.example.restsafeplatform.admin.controller;

import com.example.restsafeplatform.admin.dto.entity.CheckEmail;
import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.admin.repository.CheckEmailRepository;
import com.example.restsafeplatform.admin.service.ActivateService;
import com.example.restsafeplatform.admin.service.MailSender;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import com.example.restsafeplatform.authorization.service.JwtTokenProvider;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.example.restsafeplatform.utils.UriPath;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@RequestMapping(value = UriPath.API_PATH_PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CheckEmailController {


    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CheckEmailRepository checkEmailRepository;
    @Autowired
    private JwtTokenProvider token;
    @Autowired
    private ActivateService activateService;

    @GetMapping("email/send")
    public String sendEmailCheck(ServletRequest servletRequest) throws UserNonExistException {
        return mailSender.addCheckMail(servletRequest);
    }

    @GetMapping("confirmed/{id}/{hash}")
    public Boolean check(@PathVariable Long id, @PathVariable String hash) throws UserNonExistException {
        log.info("id  --->>{},  hash -> {}",id, hash);
       return activateService.checkURL(hash,id);
    }
}
