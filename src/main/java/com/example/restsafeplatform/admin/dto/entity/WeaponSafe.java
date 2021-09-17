package com.example.restsafeplatform.admin.dto.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "mobile_app")
public class WeaponSafe {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weapon_safe_id_seq")
    @SequenceGenerator(name = "weapon_safe_id_seq", sequenceName = "weapon_safe_id_seq", schema = "mobile_app", allocationSize = 1)
//    private static final long serialVersionUID = -7931899748847353796L;
    @Id
    private Long weaponSafeId;

    private String userEmail;

    private Float latitude;     // Широта

    private Float longitude;    // Долгота

    private String address;     // Адрес

    private String icon;        // Иконка

    private String description;         // Доп. инфорам

    @ManyToMany
    @JoinTable(
            name = "weapon_in_safe",
            joinColumns = {@JoinColumn (name = "weapon_id")},
            inverseJoinColumns = {@JoinColumn(name = "safe_id")}
    )
    private Set<WeaponInfo> weaponInfo; // Список оружий

    private Boolean isClose = false;    // Признак, что сейф закрыт

    private String title;       // Доп. имя

    private Boolean control = false;
}
