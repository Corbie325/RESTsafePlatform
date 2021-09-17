package com.example.restsafeplatform.admin.dto.response;

import com.example.restsafeplatform.admin.dto.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeaponResponse {
    private Long weaponInfoId;     // Идентификатор

    private String passport; // Идентификатор пользователя

    private Long userId;

    private String type;    // Тип

    private String name;

    private String place; //место покупки

    private String model;   // Модель

    // private Set<WeaponSafe> weaponSafe;

    // private WeaponLicense weaponLicense;  // Лицензия

    // private Unit unit;      // ID устройства
    private TtxInfo ttxInfo;
}
