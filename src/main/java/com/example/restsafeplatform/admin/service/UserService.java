package com.example.restsafeplatform.admin.service;

import com.example.restsafeplatform.admin.dto.entity.ConfidantInfo;
import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.admin.dto.request.ConfidantRequest;
import com.example.restsafeplatform.admin.dto.request.UserLocation;
import com.example.restsafeplatform.admin.dto.request.UserQuestionRequest;
import com.example.restsafeplatform.admin.dto.response.ConfidantResponse;
import com.example.restsafeplatform.admin.dto.response.UserQuestionResponse;
import com.example.restsafeplatform.admin.repository.ConfidantRepository;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import com.example.restsafeplatform.authorization.service.JwtTokenProvider;
import com.example.restsafeplatform.common.configuration.SecurityConfiguration;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ConfidantRepository confidantRepository;
    private final SecurityConfiguration securityConfiguration;

    @Autowired
    private JwtTokenProvider token;

    public UserService(UserRepository userRepository, WeaponService weaponService, ConfidantRepository confidantRepository, SecurityConfiguration securityConfiguration, JwtTokenProvider token) {
        this.userRepository = userRepository;
        this.confidantRepository = confidantRepository;
        this.securityConfiguration = securityConfiguration;
        this.token = token;
    }

    public void addUserLocation(ServletRequest servletRequest, UserLocation userLocation)throws UserNonExistException{
        String userId = token.getUserId(token.resolveToken((HttpServletRequest) servletRequest));
        Optional<UserEntity> userFromEntity = userRepository.findById(Long.valueOf(userId));
        if (userFromEntity.isEmpty()){
            throw new UserNonExistException("Пользователя не существует");
        }
        UserEntity userEntity = userFromEntity.get();
        userEntity.setLat(userLocation.getLat());
        userEntity.setLon(userLocation.getLon());
        userRepository.save(userEntity);
    }

    public UserQuestionResponse addQuestion(UserQuestionRequest userQuestionRequest, ServletRequest servletRequest) throws UserNonExistException {
        String userId = token.getUserId(token.resolveToken((HttpServletRequest) servletRequest));
        Optional<UserEntity> userFromEntity = userRepository.findById(Long.valueOf(userId));
        if (userFromEntity.isEmpty()){
            throw new UserNonExistException("Пользователя не существует");
        }
        UserEntity userEntity = userFromEntity.get();
        userEntity.setDcQuestion(userQuestionRequest.getDcQuestion());
        userEntity.setDcAnswer(userQuestionRequest.getDcAnswer());
        userRepository.save(userEntity);
        return map(userEntity);
    }

    private UserQuestionResponse map(UserEntity userEntity){
        return UserQuestionResponse
                .builder()
                .dcAnswer(userEntity.getDcAnswer())
                .dcQuestion(userEntity.getDcQuestion())
                .build();
    }

    public ConfidantResponse addConfidant(ServletRequest servletRequest, ConfidantRequest confidantRequest) throws UserNonExistException {
        String userId = token.getUserId(token.resolveToken((HttpServletRequest) servletRequest));
        Optional<UserEntity> userFromEntity = userRepository.findById(Long.valueOf(userId));
        if (userFromEntity.isEmpty()){
            throw new UserNonExistException("Пользователя не существует");
        }
        ConfidantInfo confidantInfo = ConfidantInfo
                .builder()
                .userEntity(userFromEntity.get())
                .name(confidantRequest.getName())
                .phone(confidantRequest.getPhone())
                .build();
        confidantRepository.save(confidantInfo);
        return confidantMap(confidantInfo);
    }

    private ConfidantResponse confidantMap(ConfidantInfo confidantInfo){
        return ConfidantResponse
                .builder()
                .id(confidantInfo.getId())
                .userId(confidantInfo.getUserEntity().getId())
                .name(confidantInfo.getName())
                .phone(confidantInfo.getPhone())
                .build();
    }


    public void addFastCode(ServletRequest servletRequest, String fastCode) throws UserNonExistException {
        String userId = token.getUserId(token.resolveToken((HttpServletRequest) servletRequest));
        Optional<UserEntity> userFromEntity = userRepository.findById(Long.valueOf(userId));
        if (userFromEntity.isEmpty()){
            throw new UserNonExistException("Пользователя не существует");
        }
        UserEntity userEntity = userFromEntity.get();
        userEntity.setFastCode(securityConfiguration.passwordEncoder().encode(fastCode));
        userRepository.save(userEntity);
    }
}
