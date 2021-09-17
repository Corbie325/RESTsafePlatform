package com.example.restsafeplatform.admin.dto.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "mobile_app")
public class WeaponLicense {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weapon_license_id_seq")
    @SequenceGenerator(name = "weapon_license_id_seq", sequenceName = "weapon_license_id_seq", schema = "mobile_app", allocationSize = 1)
    @Id
    private Long id;             // Идентификатор

    private String passport;  // Идентификатор пользователя

    private String type;            // Тип оружия на который выдана лицензия

    private String number;          // Номер лицензии

    private Date dateOfIssue;       // Дата выдачи

    private Date dateValidUntil;    // Действительна до

    private String placeOfIssue;    // Место выдачи

}
