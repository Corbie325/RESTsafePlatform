package com.example.restsafeplatform.postman.controller;


import com.example.restsafeplatform.common.exception.UserNonExistException;
import com.example.restsafeplatform.postman.dto.BCSettingsMessage;
import com.example.restsafeplatform.postman.dto.BcSettingResponse;
import com.example.restsafeplatform.postman.dto.CoordinatesMessage;
import com.example.restsafeplatform.postman.service.SendMessage;
import com.example.restsafeplatform.postman.service.SendMessageToInfo;
import com.example.restsafeplatform.utils.UriPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = UriPath.SEND_MESSAGE_MP, produces = MediaType.APPLICATION_JSON_VALUE)
public class PostmanController {

    private final SendMessage sendMessage;
    private final SendMessageToInfo sendMessageToInfo;

    @Autowired
    public PostmanController(SendMessage sendMessage, SendMessageToInfo sendMessageToInfo) {
        this.sendMessage = sendMessage;
        this.sendMessageToInfo = sendMessageToInfo;
    }

    @PostMapping(UriPath.GET_COORDINATES)
    @PreAuthorize("hasAuthority('users:read')")
    public void sendCoordinateMessage(@RequestBody CoordinatesMessage coordinatesMessage){
        log.info("message: {}", coordinatesMessage.getMessageType());
        log.info("serial: {}", coordinatesMessage.getSerial());
        sendMessage.sendMessage(coordinatesMessage.getMessageType(), coordinatesMessage.getSerial(), coordinatesMessage.getPassword());
    }
//
    @PostMapping(UriPath.SET_BC_SETTINGS)
    @PreAuthorize("hasAuthority('users:read')")
    public BcSettingResponse sendBCSettingsMessages(ServletRequest servletRequest, @RequestBody BCSettingsMessage bcSettingsMessage) throws URISyntaxException, UserNonExistException {
        log.info("serial: {}", bcSettingsMessage.getSerial());
        return sendMessageToInfo.getSettings(bcSettingsMessage, servletRequest);
    }
//    @PostMapping("/setColor")
//    @PreAuthorize("hasAuthority('users:read')")



}
