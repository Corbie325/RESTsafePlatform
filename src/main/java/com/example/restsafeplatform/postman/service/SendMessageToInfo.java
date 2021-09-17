package com.example.restsafeplatform.postman.service;

import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.authorization.repository.UserRepository;
import com.example.restsafeplatform.authorization.service.JwtTokenProvider;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.example.restsafeplatform.postman.dto.BCSettingsMessage;
import com.example.restsafeplatform.postman.dto.BcSettingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class SendMessageToInfo {

    private final UserRepository userRepository;

    @Autowired
    private JwtTokenProvider token;

    public SendMessageToInfo(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BcSettingResponse getSettings(BCSettingsMessage bcSettingsMessage, ServletRequest servletRequest) throws URISyntaxException, UserNonExistException {
        String userId = token.getUserId(token.resolveToken((HttpServletRequest) servletRequest));
        Optional<UserEntity> userFromEntity = userRepository.findById(Long.valueOf(userId));
        if (userFromEntity.isEmpty()){
            throw new UserNonExistException("Пользователя не существует");
        }
        String uri = "http://90.189.217.244:9010/info/BS";
        log.info("{}", uri.toString());
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> param = new HashMap<>();
        //param.put("serial", bcSettingsMessage.getSerial());
        String url = "http://90.189.217.244:9010/info/BS?serial="+bcSettingsMessage.getSerial();

        ResponseEntity<BcSettingResponse> entity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                BcSettingResponse.class,
                String.class);
        //log.info("{}", Objects.requireNonNull(entity.getBody()).getVoltage());
        return entity.getBody();
    }

}
