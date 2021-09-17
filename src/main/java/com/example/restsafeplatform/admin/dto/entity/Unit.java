package com.example.restsafeplatform.admin.dto.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "mobile_app")
public class Unit {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_id_seq")
    @SequenceGenerator(name = "unit_id_seq", sequenceName = "unit_id_seq", schema = "mobile_app", allocationSize = 1)
    @Id
    private Long id;

    private String passport; //идентификатор пользователя

    private int unitId;         //id устройства

    private boolean isActive;  //включённое устройство

    private int unitType;       //тип устройства

    private String simImei;     //imei сим карты устройства

    private String serial;      //serial устройства

    private String phoneNumber; //номер телефона сим карты

    private String mode;        //режим работы устройства

    @OneToOne
    @JoinColumn(name = "weapon_id")
    private WeaponInfo weaponInfo;       //id оружия

    private String imei;        //imei устройства

    @ManyToOne
    private UserEntity userEntity;

    private String color;

    private LocalDateTime date;
}
