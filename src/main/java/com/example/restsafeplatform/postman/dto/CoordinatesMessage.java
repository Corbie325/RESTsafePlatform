package com.example.restsafeplatform.postman.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CoordinatesMessage {

    private String serial;

    private Integer messageType;

    private String password;

}
