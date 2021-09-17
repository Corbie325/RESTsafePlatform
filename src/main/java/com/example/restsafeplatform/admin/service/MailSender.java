package com.example.restsafeplatform.admin.service;

import com.example.restsafeplatform.admin.dto.entity.CheckEmail;
import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.admin.repository.CheckEmailRepository;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import com.example.restsafeplatform.authorization.service.JwtTokenProvider;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CheckEmailRepository checkEmailRepository;

    @Autowired
    private JwtTokenProvider token;

    @Autowired
    private UserRepository userRepository;

    @Value("${smail.username}")
    private String username;

    @Async
    public void send(String emailTo, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    public String addCheckMail(ServletRequest servletRequest) throws UserNonExistException {
        String userId = token.getUserId(token.resolveToken((HttpServletRequest) servletRequest));
        Optional<UserEntity> userFromEntity = userRepository.findById(Long.valueOf(userId));
        if (userFromEntity.isEmpty()) {
            throw new UserNonExistException("Пользователя не существует");
        }
        //TODO придумать hash
        String hashed = Hashing.sha256()
                .hashString("http://90.189.217.244:7171/api/1/check/mail"+userId, StandardCharsets.UTF_8)
                .toString();
        UserEntity userEntity = userFromEntity.get();
        CheckEmail checkEmail = CheckEmail
                .builder()
                .token(hashed)
                .userId(userEntity.getId())
                .build();
        checkEmailRepository.save(checkEmail);
        send(userEntity.getEmail(), "Registration in SafePlatform", "http://90.189.217.244:7171/api/1/confirmed/"+userEntity.getId()+"/"+hashed);
        return hashed;
    }
}