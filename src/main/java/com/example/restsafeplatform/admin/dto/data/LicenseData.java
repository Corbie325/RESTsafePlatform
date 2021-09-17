package com.example.restsafeplatform.admin.dto.data;

import lombok.Data;

import java.util.Date;

@Data
public class LicenseData {

    private String type;

    private String number;          // Номер лицензии

    private Date dateOfIssue;       // Дата выдачи

    private Date dateValidUntil;    // Действительна до

    private String placeOfIssue;    // Место выдачи
}
