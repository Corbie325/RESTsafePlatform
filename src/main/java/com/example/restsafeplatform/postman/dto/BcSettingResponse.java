package com.example.restsafeplatform.postman.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BcSettingResponse {

    private String serial;

    private LocalDate version;

    private Double voltage;

    private Integer gsm;

}
