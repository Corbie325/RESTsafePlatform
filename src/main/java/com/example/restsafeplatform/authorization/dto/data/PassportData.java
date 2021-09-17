package com.example.restsafeplatform.authorization.dto.data;

import lombok.Data;

import java.util.Date;

@Data
public class PassportData {

    private String registrationAddress; // Адрес регистрации

    private String passportSerial;      // Серия паспорта

    private String passportNumber;      // Номер паспорта

    private Date passportDateOfIssue;   // Дата выдачи паспорта

    private String passportPlaceOfIssue; // Место выдачи паспорта
}
