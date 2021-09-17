package com.example.restsafeplatform.postman.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.net.Socket;
import java.net.http.WebSocket;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class SendMessage {

    public void sendMessage(Integer messageType, String serial, String password){
            Socket socket = connect();
            if (isNull(socket)) {
                log.info("socket is null");
            }
            while (nonNull(socket) && !socket.isClosed()) {
                try {
                    String systemMessage = byteCount(messageType, serial, password);
                    log.info("message отправки {}", systemMessage);
                    log.info("Message " + messageType);
                    socket.getOutputStream().write(systemMessage.getBytes(StandardCharsets.US_ASCII));
                    log.info("Response first byte " + socket.getInputStream().read());
                } catch (Exception e) {
                    log.info(e.getMessage());
                }finally {
                    close(socket);
                }
            }
    }

    private static void close(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Socket connect() {
        try {
            return new Socket("90.189.217.244", 15001);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String byteCount(Integer messageType, String serial, String password){

        String head = "#ZB#0#40#";

        Integer byteCount = 0;

        String mobileSerial = "#MP_Server#";

        String endMessage = "120915!";

        String countMessage = head + mobileSerial + serial + ";" + messageType + ";" + password + ";" + endMessage;

        for (int i = 0; i+1 < countMessage.length(); i++){
            byteCount ++;
        }

        if(byteCount >= 100)
            byteCount = byteCount + 3;
        else if(byteCount >= 10)
            byteCount = byteCount + 2;

        log.info("Длина: {}", byteCount);
//        log.info("Сообщение: {}", countMessage);
        return head + byteCount  + mobileSerial + serial + ";" + messageType + ";" + password + ";" + endMessage;
    }
}
