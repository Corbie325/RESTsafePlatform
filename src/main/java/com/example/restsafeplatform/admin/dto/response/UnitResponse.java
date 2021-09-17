package com.example.restsafeplatform.admin.dto.response;

import com.example.restsafeplatform.admin.dto.entity.UserEntity;
import com.example.restsafeplatform.admin.dto.entity.WeaponInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitResponse {
    private Long id;

    private String passport; //идентификатор пользователя

    private int unitId;         //id устройства

    private boolean isActive;  //включённое устройство

    private int unitType;       //тип устройства

    private String simImei;     //imei сим карты устройства

    private String serial;      //serial устройства

    private String phoneNumber; //номер телефона сим карты

    private String mode;        //режим работы устройства

    private WeaponInfo weaponInfo;       //id оружия

    private String imei;        //imei устройства

    private Long userId;

    private String color;

    private LocalDateTime date;
}
