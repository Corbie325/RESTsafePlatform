package com.example.restsafeplatform.admin.dto.data;

import com.example.restsafeplatform.admin.dto.data.WeaponData;
import lombok.Data;

import java.util.Date;

@Data
public class UnitData {

    private Long weapon_id;

    private String serial;

    private String passport_id;

//    private Boolean online;

//    private Date date; //TODO время, которое пригодится

//    private WeaponData weaponData;
}
