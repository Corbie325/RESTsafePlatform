package com.example.restsafeplatform.admin.service;

import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import com.example.restsafeplatform.admin.repository.CheckEmailRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@Slf4j
public class ActivateService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CheckEmailRepository checkEmailRepository;


    public Boolean checkURL(String hash, Long userId) throws UserNonExistException {
       Optional<UserEntity> userFromEntity = userRepository.findById(userId);
        if (userFromEntity.isEmpty()){
            throw new UserNonExistException("Пользователя не существует");
        }
        UserEntity userEntity = userFromEntity.get();
        if (!userEntity.getId().equals(userId)){
            throw new UserNonExistException("Пользователя не существует");
        }
        if (checkEmailRepository.findByUserId(userId).isEmpty()){
            return false;
        }
        String hashed = Hashing.sha256()
                .hashString("http://90.189.217.244:7171/api/1/check/mail"+userEntity.getId(), StandardCharsets.UTF_8)
                .toString();
        userEntity.setEmail_confirmed(1);
        userRepository.save(userEntity);
        return hashed.equals(hash);
    }

}
