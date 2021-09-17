package com.example.restsafeplatform.admin.dto.data;


import com.example.restsafeplatform.authorization.dto.data.PassportData;
import com.example.restsafeplatform.authorization.dto.model.Role;
import com.example.restsafeplatform.authorization.dto.model.Status;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserData {

//    private String token;

    private String email;    // mail

    private String password;    // пароль

    private String lastName;    // Фамилия

    private String firstName;   // Имя

    private String middleName;  // Отчество

    private String fio;         // ФИО сокращенно

    private Date birthDate;     // Дата рождения

    private PassportData passportData; // Паспортные данные

    private LicenseData licenseData;

    private Role role = Role.USER;

    private Status status = Status.ACTIVE;

    private List<String> contactPhone = new ArrayList<>(2);      // Телефоны

    private String dcQuestion;

    private UnitData unitData;
}
