package com.example.restsafeplatform.admin.service;

import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import com.example.restsafeplatform.common.exception.AlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PassportService {
    private final UserRepository userRepository;

    public PassportService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String checkPassport(String serialAndNumber){
        Optional<UserEntity> userEntity = userRepository.getUserByPassport(serialAndNumber);
        if (userEntity.isPresent()){
            return serialAndNumber;
        }else throw new AlreadyExistException("user doesn't exist");
    }
}
