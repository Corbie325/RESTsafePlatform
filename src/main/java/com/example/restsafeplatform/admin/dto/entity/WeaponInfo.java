package com.example.restsafeplatform.admin.dto.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "mobile_app")
public class WeaponInfo {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weapon_id_seq")
    @SequenceGenerator(name = "weapon_id_seq", sequenceName = "weapon_id_seq", schema = "mobile_app", allocationSize = 1)
    @Id
    private Long weaponInfoId;     // Идентификатор

    @ManyToOne
    private UserEntity userEntity;

    private String passport;

    private String type;    // Тип

    private String name;

    private String alias; //псевдоним

    private String place; //место покупки

    private String model;   // Модель

    @ManyToMany
    @JoinTable(
            name = "weapon_in_safe",
            joinColumns = {@JoinColumn (name = "safe_id")},
            inverseJoinColumns = {@JoinColumn(name = "weapon_id")}
    )
    private Set<WeaponSafe> weaponSafe;

    @OneToOne
    @JoinColumn(name = "weapon_license_id")
    private WeaponLicense weaponLicense;  // Лицензия

    @OneToOne(mappedBy = "weaponInfo")
    private Unit unit;      // ID устройства

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ttx_id")
    //@JsonIgnore
    private TtxInfo ttxInfo;
}

